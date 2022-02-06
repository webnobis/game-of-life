package com.webnobis.game.of.life.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class InitLivingTest {

	@Test
	void testOfRandom() {
		checkBothValues(InitLiving.ofRandom());
	}

	@Test
	void testOfLivingPercent() {
		checkBothValues(InitLiving.ofLivingPercent(33.333));
	}

	private void checkBothValues(InitLiving initLiving) {
		Set<Boolean> set = new HashSet<>();
		for (;;) {
			set.add(initLiving.initLiving());
			if (set.size() > 1) {
				break;
			}
		}
		assertEquals(2, set.size());
	}

}
