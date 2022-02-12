package com.webnobis.game.of.life;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * JUnit 5 extension to test Java Fx applications
 * 
 * @author steffen
 *
 */
public class GuiTestExtension
		implements ParameterResolver, BeforeAllCallback, BeforeEachCallback, InvocationInterceptor {

	static final int DEFAULT_TIMEOUT_SECONDS = 10;

	private static final int STAGE_SHOWING_CHECK_INTERVAL_MILLIS = 200;

	private static final AtomicReference<Stage> stageRef = new AtomicReference<>();

	private static final AtomicReference<Thread> platformThreadRef = new AtomicReference<>();

	private static final CountDownLatch waitForPlatform = new CountDownLatch(1);

	/**
	 * Starts only once the Java Fx platform and adds a shutdown hook to exits the
	 * platform if not exited before
	 */
	static {
		Platform.setImplicitExit(false);
		Platform.startup(() -> {
			Optional.of(Thread.currentThread()).filter(unused -> Platform.isFxApplicationThread())
					.ifPresent(platformThreadRef::set);
			waitForPlatform.countDown();
		});
		Runtime.getRuntime().addShutdownHook(new Thread(() -> Optional.ofNullable(platformThreadRef.getAndSet(null))
				.filter(thread -> thread.isAlive()).ifPresent(unused -> Platform.exit())));
	}

	/**
	 * Waits until the Java Fx platform is running
	 */
	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		waitForPlatform.await(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
	}

	/**
	 * Creates a new primary stage
	 */
	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		CountDownLatch waitForStage = new CountDownLatch(1);
		Platform.runLater(() -> {
			stageRef.set(new Stage() {
				// overwritten, because package scope method
				private boolean primary = true;
			});
			waitForStage.countDown();
		});
		waitForStage.await(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
	}

	@Override
	public void interceptTestMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext,
			ExtensionContext context) throws Throwable {
		CountDownLatch waitForStageNotMoreShowing = new CountDownLatch(1);
		Platform.runLater(() -> {
			try {
				invocation.proceed();
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
			Timer timer = new Timer("stage-checker");
			timer.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					if (!stageRef.get().isShowing()) {
						timer.cancel();
						waitForStageNotMoreShowing.countDown();
					}
				}
			}, STAGE_SHOWING_CHECK_INTERVAL_MILLIS, STAGE_SHOWING_CHECK_INTERVAL_MILLIS);
		});
		waitUntilTimeout(context.getRequiredTestMethod(), waitForStageNotMoreShowing);
	}

	private void waitUntilTimeout(Method method, CountDownLatch waitFor) throws InterruptedException, TimeoutException {
		long value;
		TimeUnit unit;
		if (method.isAnnotationPresent(Timeout.class)) {
			Timeout timeout = method.getAnnotation(Timeout.class);
			value = timeout.value();
			unit = timeout.unit();
		} else {
			value = DEFAULT_TIMEOUT_SECONDS;
			unit = TimeUnit.SECONDS;
		}
		if (!waitFor.await(value, unit)) {
			throw new TimeoutException(
					MessageFormat.format("timeout, method execution takes longer than {0}s", unit.toSeconds(value)));
		}
	}

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		Class<?> type = parameterContext.getParameter().getType();
		return Stage.class.isAssignableFrom(type);
	}

	/**
	 * Accepts Stage object
	 */
	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		Class<?> type = parameterContext.getParameter().getType();
		if (Stage.class.isAssignableFrom(type)) {
			return stageRef.get();
		}
		throw new ParameterResolutionException("only stage parameter is supported");
	}

}
