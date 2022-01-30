package com.webnobis.game.of.life;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

@ExtendWith(GuiTestExtension.class)
class GameOfLifeTest {

	@Test
	void testStart(Stage stage) {
		CountDownLatch waitForShowing = new CountDownLatch(1);
		Platform.runLater(() -> {
			waitForShowing.countDown();
			stage.show();
		});
		Platform.runLater(() -> {
			try {
				waitForShowing.await();
			} catch (InterruptedException e) {
				fail(e);
			}
			assertTrue(stage.isShowing());
			stage.close();
		});
	}

	@Test
	void testMain() {
		GameOfLife.startClass = TestStart.class;
		assertNull(TestStart.stageRef.get());
		GameOfLife.main(null);
		assertNotNull(TestStart.stageRef.get());
	}

	public static class TestStart extends Application {

		static final AtomicReference<Stage> stageRef = new AtomicReference<>();

		@Override
		public void start(Stage stage) {
			stageRef.set(stage);
		}

	}

}
