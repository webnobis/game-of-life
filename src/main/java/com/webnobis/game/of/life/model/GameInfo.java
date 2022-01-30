package com.webnobis.game.of.life.model;

import java.text.MessageFormat;

public record GameInfo(int rows, int cols, int cells, int livingCells, long generation, String gameRuleDescription) {

	public String livingCellsPercent() {
		return MessageFormat.format("{0}%", Math.round(livingCells() * 100.0 / cells() * 100) / 100.0);
	}
}
