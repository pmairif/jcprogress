/*
 * ConsoleDemo.java
 * 
 * Copyright 2008-2009 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 * 
 * tabstop=4, charset=UTF-8
 */
package net.sf.jcprogress.demo;

import java.util.Locale;

import net.sf.jcprogress.BubbleCharIndicator;
import net.sf.jcprogress.ConsoleProgressBarThread;
import net.sf.jcprogress.ConsoleProgressIndicatorThread;
import net.sf.jcprogress.ConsoleProgressThreadBase;
import net.sf.jcprogress.CounterClockwiseRotCharIndicator;
import net.sf.jcprogress.ProgressStatusProvider;
import net.sf.jcprogress.ProgressThread;

/**
 * @author pmairif
 */
public class ConsoleDemo implements ProgressStatusProvider {
	int count = 0;
	
	int countAll = 0;

	int sleep;

	public ConsoleDemo(int countAll) {
		this(countAll, 1);
	}

	public ConsoleDemo(int countAll, int sleep) {
		this.countAll = countAll;
		this.sleep = sleep;
	}

	public static void main(String[] args) {
		
		ConsoleDemo demo = new ConsoleDemo(50000);

		ConsoleProgressThreadBase progress = null;

		System.err.println("\ndefault config:");
		progress = new ConsoleProgressBarThread(System.err, demo);
		progress.start();
		demo.run();
		progress.waitToStop();	//stop progress bar
		
		System.err.println("\nwithout percentage in english:");
		progress = new ConsoleProgressBarThread(System.err, demo, Locale.ENGLISH);
		progress.setShowPercentage(false);
		progress.start();
		demo.run();
		progress.waitToStop();	//stop progress bar

		System.err.println("\nwith counter in german:");
		progress = new ConsoleProgressBarThread(System.err, demo, Locale.GERMAN);
		progress.setShowPercentage(false);
		progress.setShowCounter(true);
		progress.start();
		demo.run();
		progress.waitToStop();	//stop progress bar

		System.err.println("\nwith counter and percentage:");
		progress = new ConsoleProgressBarThread(System.err, demo);
		progress.setShowCounter(true);
		progress.setShowPercentage(true);
		progress.start();
		demo.run();
		progress.waitToStop();	//stop progress bar
		
		System.err.println("\nwith bubbles:");
		progress = new ConsoleProgressBarThread(System.err, demo);
		progress.setShowCounter(false);
		progress.setCharIndicator(new BubbleCharIndicator());
		progress.start();
		demo.run();
		progress.waitToStop();	//stop progress bar

		System.err.println("\nwithout end date:");
		progress = new ConsoleProgressBarThread(System.err, demo);
		progress.setShowCounter(false);
		progress.setShowEndDate(false);
		progress.start();
		demo.run();
		progress.waitToStop();	//stop progress bar

		System.err.println("\nwith remaining time:");
		progress = new ConsoleProgressBarThread(System.err, demo);
		progress.setShowCounter(false);
		progress.setShowEndDate(false);
		progress.setShowRemainingTime(true);
		progress.start();
		demo.run();
		progress.waitToStop();	//stop progress bar


		System.err.println("\nno progress bar, just an indicator:");
		progress = new ConsoleProgressIndicatorThread(System.err, demo);
		progress.start();
		demo.run();
		progress.waitToStop();	//stop progress bar

		System.err.println("\ncounter clockwise rotating chars:");
		progress = new ConsoleProgressIndicatorThread(System.err, demo);
		progress.setCharIndicator(new CounterClockwiseRotCharIndicator());
		progress.start();
		demo.run();
		progress.waitToStop();	//stop progress bar
		
		System.err.println("\nindicator with bubbles:");
		progress = new ConsoleProgressIndicatorThread(System.err, demo);
		progress.setCharIndicator(new BubbleCharIndicator());
		progress.start();
		demo.run();
		progress.waitToStop();	//stop progress bar
		
		System.err.println("\nprogress bar without thread:");
		progress = new ConsoleProgressBarThread(System.err, demo);
		demo.run(progress);
	}
	
	public void run() {
		for(count=0; count<countAll; count+=100) {
			try {
				Thread.sleep(sleep);
			}
			catch (InterruptedException e) {
				System.err.println("InterruptedException: "+e.getMessage());
			}
		}
	}

	public void run(ProgressThread progress) {
		for(count=0; count<countAll; count+=100) {
			progress.update(false);
			try {
				Thread.sleep(sleep);
			}
			catch (InterruptedException e) {
				System.err.println("InterruptedException: "+e.getMessage());
			}
		}
		progress.afterLastInvoking();
	}

	public String getProgressStatusText() {
		return "[I'm a dummy]";
	}

	public int getCurrentProgressCount() {
		return count;
	}

	public int getWholeProcessCount() {
		return countAll;
	}
}
