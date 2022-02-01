package com.webnobis.game.of.life.logic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import com.webnobis.game.of.life.model.Cell;

/**
 * Game rule, next generation cell living depending on neighbor cells
 * 
 * @author steffen
 *
 */
@FunctionalInterface
public interface GameRule {

	/**
	 * Calculates next living depending on neighbor cells
	 * 
	 * @param cell          cell
	 * @param neighborCells neighbor cells
	 * @return living (true) or dead (false)
	 */
	boolean nextLiving(Cell cell, Stream<Cell> neighborCells);

	/**
	 * 2G3 rule
	 * 
	 * @return game rule
	 */
	@GameRuleDescription("23/3 - 2G3 rule, the original")
	static GameRule of2G3() {
		return (cell, neighborCells) -> {
			Objects.requireNonNull(cell);
			long livingCount = Objects.requireNonNull(neighborCells).filter(Cell::living).count();
			if (Objects.requireNonNull(cell).living()) {
				return livingCount > 1L && livingCount < 4L;
			} else {
				return livingCount == 3L;
			}
		};
	}

	/**
	 * 4G3 rule
	 * 
	 * @return game rule
	 */
	@GameRuleDescription("34/3 - 4G3 rule")
	static GameRule of4G3() {
		return (cell, neighborCells) -> {
			Objects.requireNonNull(cell);
			long livingCount = Objects.requireNonNull(neighborCells).filter(Cell::living).count();
			if (Objects.requireNonNull(cell).living()) {
				return livingCount > 2L && livingCount < 5L;
			} else {
				return livingCount == 3L;
			}
		};
	}

	/**
	 * 26G3 rule
	 * 
	 * @return game rule
	 */
	@GameRuleDescription("236/3 - 26G3 rule")
	static GameRule of26G3() {
		return (cell, neighborCells) -> {
			Objects.requireNonNull(cell);
			long livingCount = Objects.requireNonNull(neighborCells).filter(Cell::living).count();
			if (Objects.requireNonNull(cell).living()) {
				return (livingCount > 1L && livingCount < 4L) || livingCount == 6L;
			} else {
				return livingCount == 3L;
			}
		};
	}

	/**
	 * G1357 rule
	 * 
	 * @return game rule
	 */
	@GameRuleDescription("1357/1357 - G1357 rule")
	static GameRule ofG1357() {
		return (cell, neighborCells) -> Objects.requireNonNull(neighborCells).filter(Cell::living).count() % 2 > 0;
	}

	/**
	 * Gets the index corresponding game rule
	 * 
	 * @param index index
	 * @return game rule
	 */
	static GameRule toGameRule(int index) {
		List<Method> gameRuleMethods = Arrays.stream(GameRule.class.getMethods())
				.filter(method -> method.getName().startsWith("of")).toList();
		if (index < 0 || index >= gameRuleMethods.size()) {
			throw new IndexOutOfBoundsException(index);
		}
		try {
			return GameRule.class.cast(gameRuleMethods.get(index).invoke(null));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Gets the index corresponding game rule description
	 * 
	 * @param index index
	 * @return description
	 * @see GameRuleDescription#value()
	 */
	static String toDescription(int index) {
		List<String> gameRuleDescriptionValues = Arrays.stream(GameRule.class.getMethods())
				.filter(method -> method.isAnnotationPresent(GameRuleDescription.class))
				.map(method -> method.getAnnotation(GameRuleDescription.class)).map(GameRuleDescription::value)
				.toList();
		if (index < 0 || index >= gameRuleDescriptionValues.size()) {
			throw new IndexOutOfBoundsException(index);
		}
		return gameRuleDescriptionValues.get(index);
	}

}
