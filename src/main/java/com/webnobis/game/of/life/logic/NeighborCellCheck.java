package com.webnobis.game.of.life.logic;

import java.util.Objects;

import com.webnobis.game.of.life.model.Cell;

@FunctionalInterface
public interface NeighborCellCheck {

	default boolean isNeighborCell(Cell cell, Cell neighborCell, int rows, int cols) {
		if (Objects.equals(cell, neighborCell)) {
			return false;
		}
		boolean rowNeighbor = isNeighborIndex(cell.row(), neighborCell.row(), rows - 1);
		boolean colNeighbor = isNeighborIndex(cell.col(), neighborCell.col(), cols - 1);
		return rowNeighbor && colNeighbor;
	}

	boolean isNeighborIndex(int index, int neighborIndex, int lastIndex);

	static NeighborCellCheck ofBorder() {
		return (index, neighborIndex, lastIndex) -> index == neighborIndex || index == neighborIndex - 1 || index == neighborIndex + 1;
	}

	static NeighborCellCheck ofBorderless() {
		return (index, neighborIndex, lastIndex) -> {
			if (index == 0) {
				return neighborIndex == lastIndex;
			}
			if (index == lastIndex) {
				return neighborIndex == 0;
			}
			return index == neighborIndex || index == neighborIndex - 1 || index == neighborIndex + 1;
		};
	}
}
