package com.webnobis.game.of.life.logic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NeighborCellCheckTest {
	
	private static final int MIN_INDEX = 0;
	
	private static final int MAX_INDEX = 5;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testOfBorder() {
		NeighborCellCheck neighborCellCheck = NeighborCellCheck.ofBorder();
		assertTrue(neighborCellCheck.isNeighborIndex(MIN_INDEX, MIN_INDEX + 1, MAX_INDEX));
		assertFalse(neighborCellCheck.isNeighborIndex(MIN_INDEX, MAX_INDEX, MAX_INDEX));
	}

	@Test
	void testOfBorderless() {
		NeighborCellCheck neighborCellCheck = NeighborCellCheck.ofBorderless();
		assertTrue(neighborCellCheck.isNeighborIndex(MIN_INDEX, MIN_INDEX + 1, MAX_INDEX));
		assertTrue(neighborCellCheck.isNeighborIndex(MIN_INDEX, MAX_INDEX, MAX_INDEX));
	}

}
