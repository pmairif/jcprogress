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
public class AcceleratingTest extends ConsoleDemo {

	public AcceleratingTest(int countAll, int sleep) {
		super(countAll, sleep);
	}

	public AcceleratingTest(int countAll) {
		super(countAll);
	}
	
	@Override
	public void run() {
		int step1 = 2000;
		int step2 = 500000;
		
		//+=100
		for(count=0; count<step1; count+=100) {
			sleep();
		}
		
		//stalled
		for (int i=0; i<10; i++) {
			sleep();
		}

		//+=10,000
		for(count=step1; count<step2; count+=10000) {
			sleep();
		}

		//+=10
		for(count=step2; count<countAll; count+=10) {
			sleep();
		}
	}

	void sleep() {
		try {
			Thread.sleep(sleep);
		}
		catch (InterruptedException e) {
			System.err.println("InterruptedException: "+e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		
		AcceleratingTest test = new AcceleratingTest(20000000, 1000);

		ConsoleProgressBarThread progress = new ConsoleProgressBarThread(System.err, test);
		progress.setInfoUpdateIntervalMs(1000);
		progress.setStalledTimeWindowMs(1500);
		progress.setShowRemainingTime(false);
		progress.setShowCounter(true);

		progress.start();
		test.run();
		progress.waitToStop();	//stop progress bar
	}
}
