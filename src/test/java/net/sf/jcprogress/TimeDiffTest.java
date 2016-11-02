/*
 * TimeDiffTest.java
 *
 * Copyright 2008 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 * 
 * tabstop=4, charset=UTF-8
 */
package net.sf.jcprogress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

@SuppressWarnings("nls")
public class TimeDiffTest {

	public TimeDiffTest() {
		super();
	}

	@Test
	public void testMilliSeconds() {
		TimeDiff td = new TimeDiff(5);	//5ms
		assertTrue(0.005f == td.getSeconds());
		assertEquals(0, td.getMinutes());
		assertEquals(0, td.getHours());
		assertEquals(0, td.getDays());
	}

	@Test
	public void testSeconds() {
		{
			TimeDiff td = new TimeDiff(5000);	//5s = 5000ms
			assertTrue(5.0f == td.getSeconds());
			assertEquals(0, td.getMinutes());
			assertEquals(0, td.getHours());
			assertEquals(0, td.getDays());
		}
		{
			TimeDiff td = new TimeDiff(5600);
			assertTrue(5.6f == td.getSeconds());
			assertEquals(0, td.getMinutes());
			assertEquals(0, td.getHours());
			assertEquals(0, td.getDays());
		}
		{
			TimeDiff td = new TimeDiff(5640);
			assertTrue(5.64f == td.getSeconds());
			assertEquals(0, td.getMinutes());
			assertEquals(0, td.getHours());
			assertEquals(0, td.getDays());
		}
		{
			TimeDiff td = new TimeDiff(5041);
			assertTrue(5.041f == td.getSeconds());
			assertEquals(0, td.getMinutes());
			assertEquals(0, td.getHours());
			assertEquals(0, td.getDays());
		}
	}

	@Test
	public void testMinutes() {
		{
			TimeDiff td = new TimeDiff(240000);	//4min = 4 * 60 * 1000 ms
			assertTrue(0.0f == td.getSeconds());
			assertEquals(4, td.getMinutes());
			assertEquals(0, td.getHours());
			assertEquals(0, td.getDays());
		}
		{
			TimeDiff td = new TimeDiff(241000);	//4min 1s
			assertTrue(1.0f == td.getSeconds());
			assertEquals(4, td.getMinutes());
			assertEquals(0, td.getHours());
			assertEquals(0, td.getDays());
		}
		{
			TimeDiff td = new TimeDiff(241030);	//4min 1.03s
			assertTrue(1.03f == td.getSeconds());
			assertEquals(4, td.getMinutes());
			assertEquals(0, td.getHours());
			assertEquals(0, td.getDays());
		}
	}

	@Test
	public void testHours() {
		{
			TimeDiff td = new TimeDiff(10800000);	//3h = 3 * 60 * 60 * 1000 ms
			assertTrue(0.0f == td.getSeconds());
			assertEquals(0, td.getMinutes());
			assertEquals(3, td.getHours());
			assertEquals(0, td.getDays());
		}
		{
			TimeDiff td = new TimeDiff(11040000);	//3h 4min
			assertTrue(0.0f == td.getSeconds());
			assertEquals(4, td.getMinutes());
			assertEquals(3, td.getHours());
			assertEquals(0, td.getDays());
		}
		{
			TimeDiff td = new TimeDiff(11041000);	//3h 4min 1s
			assertTrue(1.0f == td.getSeconds());
			assertEquals(4, td.getMinutes());
			assertEquals(3, td.getHours());
			assertEquals(0, td.getDays());
		}
		{
			TimeDiff td = new TimeDiff(11041200);	//3h 4min 1.2s
			assertTrue(1.2f == td.getSeconds());
			assertEquals(4, td.getMinutes());
			assertEquals(3, td.getHours());
			assertEquals(0, td.getDays());
		}
		{
			TimeDiff td = new TimeDiff(10800030);	//3h 0.03s
			assertTrue(0.03f == td.getSeconds());
			assertEquals(0, td.getMinutes());
			assertEquals(3, td.getHours());
			assertEquals(0, td.getDays());
		}
	}

	@Test
	public void testDays() {
		{
			TimeDiff td = new TimeDiff(172800000);	//2d = 2 * 24 * 60 * 60 * 1000 ms
			assertTrue(0.0f == td.getSeconds());
			assertEquals(0, td.getMinutes());
			assertEquals(0, td.getHours());
			assertEquals(2, td.getDays());
		}
		{
			TimeDiff td = new TimeDiff(255600000);	//2d 23h
			assertTrue(0.0f == td.getSeconds());
			assertEquals(0, td.getMinutes());
			assertEquals(23, td.getHours());
			assertEquals(2, td.getDays());
		}
		{
			TimeDiff td = new TimeDiff(259020000);	//2d 23h 57min
			assertTrue(0.0f == td.getSeconds());
			assertEquals(57, td.getMinutes());
			assertEquals(23, td.getHours());
			assertEquals(2, td.getDays());
		}
		{
			TimeDiff td = new TimeDiff(259057000);	//2d 23h 57min 37s
			assertTrue(37.0f == td.getSeconds());
			assertEquals(57, td.getMinutes());
			assertEquals(23, td.getHours());
			assertEquals(2, td.getDays());
		}
		{
			TimeDiff td = new TimeDiff(259057800);	//2d 23h 57min 37.8s
			assertTrue(37.8f == td.getSeconds());
			assertEquals(57, td.getMinutes());
			assertEquals(23, td.getHours());
			assertEquals(2, td.getDays());
		}
		{
			TimeDiff td = new TimeDiff(172800200);	//2d 0.2s
			assertTrue(0.2f == td.getSeconds());
			assertEquals(0, td.getMinutes());
			assertEquals(0, td.getHours());
			assertEquals(2, td.getDays());
		}
	}
	
	@Test
	public void testToString() {
		TimeDiff td1 = new TimeDiff(172800000);					//2d
		assertEquals("2d", td1.toString());

		TimeDiff td2 = new TimeDiff(255600000);					//2d 23h
		assertEquals("2d 23h", td2.toString());
		
		TimeDiff td3 = new TimeDiff(259020000);					//2d 23h 57min
		assertEquals("2d 23h 57min", td3.toString());
		
		TimeDiff td4 = new TimeDiff(259057000);					//2d 23h 57min 37s
		assertEquals("2d 23h 57min 37s", td4.toString());
		
		TimeDiff td5 = new TimeDiff(259057800);					//2d 23h 57min 37.8s
		assertEquals("2d 23h 57min 37.8s", td5.toString(Locale.ENGLISH));
		
		TimeDiff td6 = new TimeDiff(172800200);					//2d 0.2s
		assertEquals("2d 0.2s", td6.toString(Locale.ENGLISH));
		assertEquals("2d 0,2s", td6.toString(Locale.GERMAN));

		TimeDiff td8 = new TimeDiff(10800000);					//3h
		assertEquals("3h", td8.toString());
		
		TimeDiff td9 = new TimeDiff(11040000);					//3h 4min
		assertEquals("3h 4min", td9.toString());
		
		TimeDiff td10 = new TimeDiff(11041000);					//3h 4min 1s
		assertEquals("3h 4min 1s", td10.toString());
		
		TimeDiff td11 = new TimeDiff(11041200);					//3h 4min 1.2s
		assertEquals("3h 4min 1.2s", td11.toString(Locale.ENGLISH));
		
		TimeDiff td12 = new TimeDiff(10800030);					//3h 0.03s
		assertEquals("3h 0.03s", td12.toString(Locale.ENGLISH));
		
		TimeDiff td13 = new TimeDiff(240000);					//4min
		assertEquals("4min", td13.toString());
		
		TimeDiff td14 = new TimeDiff(241000);					//4min 1s
		assertEquals("4min 1s", td14.toString());
		
		TimeDiff td15 = new TimeDiff(241030);					//4min 1.03s
		assertEquals("4min 1.03s", td15.toString(Locale.ENGLISH));
		
		TimeDiff td16 = new TimeDiff(5000);						//5s
		assertEquals("5s", td16.toString());
		
		TimeDiff td17 = new TimeDiff(5600);
		assertEquals("5.6s", td17.toString(Locale.ENGLISH));
		
		TimeDiff td18 = new TimeDiff(5640);
		assertEquals("5.64s", td18.toString(Locale.ENGLISH));
		
		TimeDiff td19 = new TimeDiff(5041);
		assertEquals("5.041s", td19.toString(Locale.ENGLISH));
	}
}
