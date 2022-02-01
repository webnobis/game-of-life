package com.webnobis.game.of.life.view;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import com.webnobis.game.of.life.model.Config;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

/**
 * Resize listener
 * 
 * @author steffen
 *
 */
public class ResizeListener {

	private final Window parent;

	private final GridPane gamePane;

	private final Supplier<Config> configSupplier;

	private final AtomicReference<Double> cellWidthRef;

	private final AtomicReference<Double> cellHeightRef;

	private final AtomicInteger resizeWaitCounter;

	/**
	 * Resize listener which updates the radius of all matrix cells
	 * 
	 * @param parent         parent
	 * @param gamePane       game pane
	 * @param configSupplier configuration supplier
	 * @see CellPane#update(double)
	 */
	public ResizeListener(Window parent, GridPane gamePane, Supplier<Config> configSupplier) {
		this.parent = Objects.requireNonNull(parent);
		this.gamePane = Objects.requireNonNull(gamePane);
		this.configSupplier = Objects.requireNonNull(configSupplier);
		cellWidthRef = new AtomicReference<>(0.0);
		cellHeightRef = new AtomicReference<>(0.0);
		resizeWaitCounter = new AtomicInteger();
	}

	/**
	 * Width has changed
	 * 
	 * @param newValue new value
	 */
	public void widthChanged(double newValue) {
		double cellWidth = newValue / configSupplier.get().cols();
		cellWidthRef.set(cellWidth);
		resize();
	}

	/**
	 * Height has changed
	 * 
	 * @param newValue new value
	 */
	public void heightChanged(double newValue) {
		double cellHeight = newValue / configSupplier.get().rows();
		cellHeightRef.set(cellHeight);
		resize();
	}

	private void resize() {
		if (!gamePane.getChildren().isEmpty() && resizeWaitCounter.getAndDecrement() < 0) {
			Platform.runLater(() -> {
				double cellRadius = Math.min(cellWidthRef.get(), cellHeightRef.get()) / 2;
				gamePane.getChildren().stream().map(CellPane.class::cast).forEach(cell -> cell.update(cellRadius));
				resizeWaitCounter.set(2);
				parent.sizeToScene();
			});
		}
	}

}
