package com.webnobis.game.of.life.model;

public record Config(int rows, int cols, long nextLivingPeriodMilliseconds, int initLivingPercent,
		boolean randomInitLiving, boolean neighborCellBorderless, boolean sameRowsAndCols, int gameRuleIndex) {

	public static Config getDefault() {
		return new Config(10, 10, 1000L, 50, false, true, true, 0);
	}

}
