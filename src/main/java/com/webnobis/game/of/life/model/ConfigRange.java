package com.webnobis.game.of.life.model;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public interface ConfigRange {

	static List<Integer> getRowsOrColsRange() {
		return IntStream.concat(IntStream.rangeClosed(3, 100), IntStream.rangeClosed(101, 500).filter(i -> i % 5 == 0))
				.boxed().toList();
	}

	static List<Long> getNextLivingPeriodMilliseconds() {
		return LongStream.concat(
				IntStream.of(10, 50, 100, 150, 200, 250, 333, 500, 667, 750, 1000, 1500).asLongStream(),
				IntStream.concat(IntStream.rangeClosed(2, 10), IntStream.rangeClosed(11, 60).filter(i -> i % 5 == 0))
						.mapToLong(TimeUnit.SECONDS::toMillis))
				.boxed().toList();
	}

	static List<Integer> getInitLivingPercent() {
		return IntStream.of(0, 5, 10, 15, 20, 25, 33, 40, 50, 60, 67, 75, 80, 85, 90, 95, 100).boxed().toList();
	}

}
