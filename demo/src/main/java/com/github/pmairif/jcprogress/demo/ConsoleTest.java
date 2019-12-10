/*
 * Copyright 2009-2019 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 *
 */
package com.github.pmairif.jcprogress.demo;

import com.github.pmairif.jcprogress.ConsoleProgressBarThread;

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
