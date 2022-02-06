package com.webnobis.game.of.life.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webnobis.game.of.life.model.Cell;
import com.webnobis.game.of.life.model.Config;

class MatrixBuilderTest {

	private static final int ROWS = 300;

	private static final int COLS = 400;

	private MatrixBuilder matrixBuilder;

	@BeforeEach
	void setUp() throws Exception {
		matrixBuilder = MatrixBuilder.ofConfig(new Config(ROWS, COLS, -1, -1, true, true, true, 0));
	}

	@Test
	void testNext() {
		List<Cell> cells = matrixBuilder.next();
		assertEquals(ROWS * COLS, cells.size());
		Stream.of(true, false).forEach(living -> assertTrue(cells.stream().map(Cell::living).anyMatch(living::equals)));
	}

}
