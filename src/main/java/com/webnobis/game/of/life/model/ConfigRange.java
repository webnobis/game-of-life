package com.webnobis.game.of.life.model;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Configuration range of configuration parts
 * 
 * @author steffen
 */
public interface ConfigRange {

	/**
	 * Usable values of rows and columns
	 * 
	 * @return values
	 * @see Config#rows()
	 * @see Config#cols()
	 */
	static List<Integer> getRowsOrColsRange() {
		return IntStream.concat(IntStream.rangeClosed(3, 100), IntStream.rangeClosed(101, 500).filter(i -> i % 5 == 0))
				.boxed().toList();
	}

	/**
	 * Usable values of next living period milliseconds
	 * 
	 * @return values
	 * @see Config#nextLivingPeriodMilliseconds()
	 */
	static List<Long> getNextLivingPeriodMilliseconds() {
		return LongStream.concat(
				IntStream.of(10, 50, 100, 150, 200, 250, 333, 500, 667, 750, 1000, 1500).asLongStream(),
				IntStream.concat(IntStream.rangeClosed(2, 10), IntStream.rangeClosed(11, 60).filter(i -> i % 5 == 0))
						.mapToLong(TimeUnit.SECONDS::toMillis))
				.boxed().toList();
	}

	/**
	 * Usable values of initial living percent
	 * 
	 * @return values
	 * @see Config#initLivingPercent()
	 */
	static List<Integer> getInitLivingPercent() {
		return IntStream.of(0, 5, 10, 15, 20, 25, 33, 40, 50, 60, 67, 75, 80, 85, 90, 95, 100).boxed().toList();
	}

}
