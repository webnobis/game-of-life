package com.webnobis.game.of.life;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

class GameOfLifeMainTest {

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
			Platform.exit();
		}

	}

}
