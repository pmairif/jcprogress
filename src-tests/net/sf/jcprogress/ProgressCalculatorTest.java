/*
 * ProgressCalculatorTest.java (jcprogress)
 *
 * Copyright 2009 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 *
 * tabstop=4, charset=UTF-8
 */
package net.sf.jcprogress;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * testing {@link ProgressCalculator}
 * @author pmairif
 */
public class ProgressCalculatorTest {

	private ProgressCalculator calc;
	
	//	/**
	//	 * Logger for this class
	//	 */
	//	private final Log logger = LogFactory.getLog(ProgressCalculatorTest.class);

	@Before
	public void setUp() throws Exception {
		calc = new ProgressCalculator(1000, 500);
	}

	/**
	 * Test method for {@link net.sf.jcprogress.ProgressCalculator#getCurrentPercentage()}.
	 */
	@Test
	public void testGetCurrentPercentage() {
		assertEquals(0, calc.getProgressCount());
		assertEquals(0d, calc.getCurrentPercentage(), 0);
		
		calc.setProgressCount(500);
		assertEquals(0.5d, calc.getCurrentPercentage(), 0);

		calc.setProgressCount(1);
		assertEquals(0.001d, calc.getCurrentPercentage(), 0);
	}

	/**
	 * Test method for {@link net.sf.jcprogress.ProgressCalculator#estimateWholeTime(long)}.
	 */
	@Test
	public void testEstimateWholeTime() {
		long start = calc.getStartTime();
		
		assertEquals(0, calc.getProgressCount());
		assertEquals(1000, calc.getProgressWhole());
//		assertTrue(calc.isStalled());
		
		assertEquals(0, calc.estimateWholeTime(start));
		assertEquals(0, calc.estimateWholeTime(start+100));
		
		calc.setProgressCount(500);	//half of the work is done
		assertFalse(calc.isStalled());
		assertEquals(0.5d, calc.getCurrentPercentage(), 0);
		assertEquals(2000, calc.estimateWholeTime(start+1000));	//took 1s for 500 items, estimation is 2s for 1000 items

		calc.setProgressCount(1000);	//whole work is done
		assertFalse(calc.isStalled());
		assertEquals(1d, calc.getCurrentPercentage(), 0);
		assertEquals(1000, calc.estimateWholeTime(start+1000));	//took 1s for 1000 items, estimation is 1s, too
	}
	
	@Test
	public void testIsStalled() {
		long start = calc.getStartTime();
		
		assertEquals(0, calc.getProgressCount());
		assertEquals(1000, calc.getProgressWhole());
		
		assertFalse(calc.isStalled(start));		//never stalled at the beginning
		assertFalse(calc.isStalled(start+409));	//500 is the inactivity window
		assertTrue(calc.isStalled(start+500));
		assertTrue(calc.isStalled(start+501));
		assertTrue(calc.isStalled(start+1000));
		
		calc.setProgressCount(500);	//half of the work is done
		assertFalse(calc.isStalled(start));
		assertFalse(calc.isStalled(start+1000));
	}
}
