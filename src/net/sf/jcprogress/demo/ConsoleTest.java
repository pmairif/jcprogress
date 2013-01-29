/*
 * ConsoleTest.java
 * 
 * Copyright 2008-2009 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 * 
 * tabstop=4, charset=UTF-8
 */
package net.sf.jcprogress.demo;

import net.sf.jcprogress.ConsoleProgressBarThread;

/**
 * @author pmairif
 */
public class ConsoleTest {

	public static void main(String[] args) {
		
		ConsoleDemo test = new ConsoleDemo(2000000000, 1000);

		ConsoleProgressBarThread progress = new ConsoleProgressBarThread(System.err, test);
		progress.setInfoUpdateIntervalMs(2000);
		progress.setShowRemainingTime(true);

		progress.start();
		test.run();
		progress.waitToStop();	//stop progress bar
	}
}
