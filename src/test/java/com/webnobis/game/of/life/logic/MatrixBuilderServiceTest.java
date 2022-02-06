package com.webnobis.game.of.life.logic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webnobis.game.of.life.model.Cell;

class MatrixBuilderServiceTest {

	private MatrixBuilder matrixBuilder;

	@BeforeEach
	void setUp() throws Exception {
		matrixBuilder = new MatrixBuilderService(100, 200, () -> true, (i1, i2, i3) -> true,
				(cell, neighborCells) -> false);
	}

	@Test
	void testNextInit() {
		List<Cell> cells = matrixBuilder.next();
		assertTrue(cells.stream().map(Cell::living).allMatch(Boolean.TRUE::equals));
	}

	@Test
	void testNext() {
		matrixBuilder.next();
		List<Cell> cells = matrixBuilder.next();
		assertTrue(cells.stream().map(Cell::living).allMatch(Boolean.FALSE::equals));
	}

}
