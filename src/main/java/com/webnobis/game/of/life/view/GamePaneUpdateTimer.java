package com.webnobis.game.of.life.view;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import com.webnobis.game.of.life.logic.GameRule;
import com.webnobis.game.of.life.logic.MatrixBuilder;
import com.webnobis.game.of.life.model.Cell;
import com.webnobis.game.of.life.model.Config;
import com.webnobis.game.of.life.model.GameInfo;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;

class GamePaneUpdateTimer extends TimerTask {

	private static final AtomicReference<Timer> currentTimerRef = new AtomicReference<>();

	private final GridPane gamePane;

	private final Config config;

	private final Consumer<GameInfo> gameInfoConsumer;

	private final MatrixBuilder matrixBuilder;

	private final AtomicLong generationRef;

	private final String gameRuleDescription;

	public GamePaneUpdateTimer(GridPane gamePane, double radius, Config config, Consumer<GameInfo> gameInfoConsumer) {
		super();
		this.gamePane = Objects.requireNonNull(gamePane);
		this.config = Objects.requireNonNull(config);
		this.gameInfoConsumer = Objects.requireNonNull(gameInfoConsumer);
		matrixBuilder = MatrixBuilder.ofConfig(config);
		generationRef = new AtomicLong();
		gameRuleDescription = GameRule.toDescription(config.gameRuleIndex());
		init(radius);

		Timer timer = new Timer(GamePaneUpdateTimer.class.getSimpleName());
		Optional.ofNullable(currentTimerRef.getAndSet(timer)).ifPresent(Timer::cancel);
		timer.scheduleAtFixedRate(this, 0, config.nextLivingPeriodMilliseconds());
	}

	private void init(double radius) {
		gamePane.getChildren().clear();
		List<Cell> cells = matrixBuilder.next();
		GameInfo gameInfo = new GameInfo(config.rows(), config.cols(), cells.size(), getLivingCells(cells),
				generationRef.incrementAndGet(), gameRuleDescription);
		cells.forEach(cell -> gamePane.add(new CellPane(radius, cell.living()), cell.col(), cell.row()));
		gameInfoConsumer.accept(gameInfo);
	}

	private static int getLivingCells(List<Cell> cells) {
		return (int) cells.stream().filter(Cell::living).count();
	}

	@Override
	public void run() {
		Platform.runLater(() -> {
			List<Cell> cells = matrixBuilder.next();
			GameInfo gameInfo = new GameInfo(config.rows(), config.cols(), cells.size(), getLivingCells(cells),
					generationRef.incrementAndGet(), gameRuleDescription);
			cells.forEach(cell -> CellPane.class
					.cast(gamePane.getChildren().get(cell.col() + (cell.row() * config.cols()))).update(cell.living()));
			gameInfoConsumer.accept(gameInfo);
		});
	}

}
