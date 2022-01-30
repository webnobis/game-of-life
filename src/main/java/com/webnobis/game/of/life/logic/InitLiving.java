package com.webnobis.game.of.life.logic;

import java.security.SecureRandom;

@FunctionalInterface
public interface InitLiving {
	
	boolean initLiving();
	
	static InitLiving ofRandom() {
		SecureRandom random = new SecureRandom();
		return random::nextBoolean;
	}
	
	static InitLiving ofLivingPercent(double percent) {
		SecureRandom random = new SecureRandom();
		return () -> random.nextDouble(100.0) < percent;
	}

}
