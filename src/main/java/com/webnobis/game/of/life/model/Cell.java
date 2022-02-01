package com.webnobis.game.of.life.model;

/**
 * Cell
 * 
 * @author steffen
 *
 */
public record Cell(int row, int col, boolean living) {

	/**
	 * New updated cell with updated living
	 * 
	 * @param nextLiving next living
	 * @return updated cell
	 */
	public Cell getUpdated(boolean nextLiving) {
		return new Cell(row, col, nextLiving);
	}
}
