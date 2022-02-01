package com.webnobis.game.of.life.model;

import java.text.MessageFormat;

/**
 * Game information
 * 
 * @author steffen
 *
 */
public record GameInfo(int rows, int cols, int cells, int livingCells, long generation, String gameRuleDescription) {

	/**
	 * Living cells percent
	 * 
	 * @return living cells percent
	 */
	public String livingCellsPercent() {
		return MessageFormat.format("{0}%", Math.round(livingCells() * 100.0 / cells() * 100) / 100.0);
	}
}
