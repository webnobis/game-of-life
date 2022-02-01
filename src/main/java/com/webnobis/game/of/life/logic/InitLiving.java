package com.webnobis.game.of.life.logic;

import java.security.SecureRandom;

/**
 * Initial living
 * 
 * @author steffen
 *
 */
@FunctionalInterface
public interface InitLiving {

	/**
	 * Initial living
	 * 
	 * @return living (true) or dead (false)
	 */
	boolean initLiving();

	/**
	 * Fifty-fifty random initial living
	 * 
	 * @return initial living
	 */
	static InitLiving ofRandom() {
		SecureRandom random = new SecureRandom();
		return random::nextBoolean;
	}

	/**
	 * Random weight percent initial living
	 * 
	 * @param percent 0..100 percent of living
	 * @return initial living
	 */
	static InitLiving ofLivingPercent(double percent) {
		SecureRandom random = new SecureRandom();
		return () -> random.nextDouble(100.0) < percent;
	}

}
