package com.webnobis.game.of.life.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

class GameRuleTest {

	@Test
	void testToGameRule() {
		List<GameRule> gameRules = Arrays.asList(GameRule.of2G3(), GameRule.of4G3(), GameRule.of26G3(),
				GameRule.ofG1357());
		IntStream.range(0, gameRules.size())
				.forEach(index -> assertEquals(gameRules.get(index), GameRule.toGameRule(index)));
	}

	@Test
	void testToDescription() {
		List<String> descriptions = Arrays.stream(GameRule.class.getAnnotationsByType(GameRuleDescription.class))
				.map(GameRuleDescription::value).toList();
		IntStream.range(0, descriptions.size())
				.forEach(index -> assertEquals(descriptions.get(index), GameRule.toDescription(index)));
	}

}
