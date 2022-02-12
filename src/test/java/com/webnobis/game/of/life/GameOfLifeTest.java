package com.webnobis.game.of.life;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.webnobis.commons.fx.test.GuiTestExtension;

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
	void testMain() throws InterruptedException {
		CountDownLatch waitForStarter = new CountDownLatch(1);
		GameOfLife.starter = appClass -> {
			assertEquals(GameOfLife.class, appClass);
			waitForStarter.countDown();
		};
		GameOfLife.main(null);
		waitForStarter.await();
	}

}
