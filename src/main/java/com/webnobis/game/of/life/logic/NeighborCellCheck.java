package com.webnobis.game.of.life.logic;

import java.util.Objects;

import com.webnobis.game.of.life.model.Cell;

/**
 * Neighbor cell check
 * 
 * @author steffen
 *
 */
@FunctionalInterface
public interface NeighborCellCheck {

	/**
	 * Checks both cells of neighborhood
	 * 
	 * @param cell         cell
	 * @param neighborCell neighbor cell to check
	 * @param rows         rows
	 * @param cols         columns
	 * @return true if neighbor cell
	 * @see NeighborCellCheck#isNeighborIndex(int, int, int)
	 */
	default boolean isNeighborCell(Cell cell, Cell neighborCell, int rows, int cols) {
		if (Objects.equals(cell, neighborCell)) {
			return false;
		}
		boolean rowNeighbor = isNeighborIndex(cell.row(), neighborCell.row(), rows - 1);
		boolean colNeighbor = isNeighborIndex(cell.col(), neighborCell.col(), cols - 1);
		return rowNeighbor && colNeighbor;
	}

	/**
	 * Checks if both index are side by side
	 * 
	 * @param index         index
	 * @param neighborIndex neighbor index to check
	 * @param lastIndex     last index
	 * @return true if neighbor index
	 */
	boolean isNeighborIndex(int index, int neighborIndex, int lastIndex);

	/**
	 * Neighbor cell check which respects the matrix border.<br>
	 * If a cell is beside the border, that side hasn't neighbors
	 * 
	 * @return neighbor cell check
	 */
	static NeighborCellCheck ofBorder() {
		return (index, neighborIndex, lastIndex) -> index == neighborIndex || index == neighborIndex - 1
				|| index == neighborIndex + 1;
	}

	/**
	 * Neighbor cell check which doesn't know border.<br>
	 * Neighbor cells are all around
	 * 
	 * @return neighbor cell check
	 */
	static NeighborCellCheck ofBorderless() {
		return (index, neighborIndex, lastIndex) -> {
			if (index == neighborIndex || index == neighborIndex - 1 || index == neighborIndex + 1) {
				return true;
			}
			if (index == 0) {
				return neighborIndex == lastIndex;
			}
			if (index == lastIndex) {
				return neighborIndex == 0;
			}
			return false;
		};
	}
}
