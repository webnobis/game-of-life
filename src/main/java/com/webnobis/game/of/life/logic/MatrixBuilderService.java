package com.webnobis.game.of.life.logic;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import com.webnobis.game.of.life.model.Cell;

class MatrixBuilderService implements MatrixBuilder {

	private final int rows;

	private final int cols;

	private final InitLiving initLiving;

	private final NeighborCellCheck neighborCellCheck;

	private final GameRule gameRule;

	private final List<Cell> cells;

	public MatrixBuilderService(int rows, int cols, InitLiving initLiving, NeighborCellCheck neighborCellCheck,
			GameRule gameRule) {
		super();
		this.rows = rows;
		this.cols = cols;
		this.initLiving = Objects.requireNonNull(initLiving);
		this.neighborCellCheck = Objects.requireNonNull(neighborCellCheck);
		this.gameRule = Objects.requireNonNull(gameRule);
		cells = new CopyOnWriteArrayList<>();
	}

	@Override
	public List<Cell> next() {
		if (cells.isEmpty()) {
			cells.addAll(IntStream.range(0, rows).boxed()
					.flatMap(row -> IntStream.range(0, cols)
							.mapToObj(col -> new Cell(row, col, Objects.requireNonNull(initLiving).initLiving())))
					.toList());
		} else {
			cells.clear();
			cells.addAll(
					cells.stream()
							.map(cell -> cell.getUpdated(gameRule.nextLiving(cell, cells.stream().filter(
									checkCell -> neighborCellCheck.isNeighborCell(cell, checkCell, rows, cols)))))
							.toList());
		}
		return cells;
	}

}
