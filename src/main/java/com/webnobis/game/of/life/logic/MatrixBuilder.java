package com.webnobis.game.of.life.logic;

import java.util.List;
import java.util.Objects;

import com.webnobis.game.of.life.model.Cell;
import com.webnobis.game.of.life.model.Config;

/**
 * Matrix builder
 * 
 * @author steffen
 *
 */
@FunctionalInterface
public interface MatrixBuilder {

	/**
	 * Matrix of cells
	 * 
	 * @return cell matrix
	 */
	List<Cell> next();

	/**
	 * Configuration depending matrix builder
	 * 
	 * @param config configuration
	 * @return matrix builder
	 * @see GameRule
	 * @see InitLiving
	 * @see NeighborCellCheck
	 * @see MatrixBuilderService#MatrixBuilderService(int, int, InitLiving,
	 *      NeighborCellCheck, GameRule)
	 */
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
