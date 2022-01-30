package com.webnobis.game.of.life.model;

public record Cell(int row, int col, boolean living) {

	public Cell getUpdated(boolean nextLiving) {
		return new Cell(row, col, nextLiving);
	}
}
