package com.webnobis.game.of.life.logic;

import java.util.List;
import java.util.Objects;

import com.webnobis.game.of.life.model.Cell;
import com.webnobis.game.of.life.model.Config;

@FunctionalInterface
public interface MatrixBuilder {

	List<Cell> next();

	static MatrixBuilder ofConfig(Config config) {
		Objects.requireNonNull(config);
		InitLiving initLiving = config.randomInitLiving() ? InitLiving.ofRandom()
				: InitLiving.ofLivingPercent(config.initLivingPercent());
		NeighborCellCheck neighborCellCheck = config.neighborCellBorderless() ? NeighborCellCheck.ofBorderless()
				: NeighborCellCheck.ofBorder();
		return new MatrixBuilderService(config.rows(), config.cols(), initLiving, neighborCellCheck,
				GameRule.toGameRule(config.gameRuleIndex()));
	}

}
