/*
 * Checkpoint.java (jcprogress)
 *
 * Copyright 2009 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 *
 * tabstop=4, charset=UTF-8
 */
package net.sf.jcprogress;

/**
 * checkpoint
 * @author pmairif
 */
public class Checkpoint {

	/**
	 * elapsed time in milliseconds, when checkpoint was reached
	 */
	private final long startElapsedMs;
	
	/**
	 * progress count, when checkpoint was reached
	 */
	private final int startProgessCount;

	private final ProgressCalculator calculator;
	
//	/**
//	 * Logger for this class
//	 */
//	private final Log logger = LogFactory.getLog(Checkpoint.class);

	/**
	 * @param progressWhole
	 * 		number of items to process at all
	 * @param stalledTimeWindowMs
	 *		milliseconds after which stalled activity is assumed 
	 */
	public Checkpoint(int progressWhole, int progressCurrent, long elapsedMs, int stalledTimeWindowMs) {
		this(progressWhole, progressCurrent, elapsedMs, stalledTimeWindowMs, System.currentTimeMillis());
	}

	/**
	 * @param startTime
	 * 		start time in system time milliseconds
	 * @param progressWhole
	 * 		number of items to process at all
	 * @param stalledTimeWindowMs
	 *		milliseconds after which stalled activity is assumed 
	 */
	public Checkpoint(int progressWhole, int progressCurrent, long elapsedMs, int stalledTimeWindowMs, long now) {
		int progressRemaining = progressWhole - progressCurrent;
		
		this.startElapsedMs = elapsedMs;
		this.startProgessCount = progressCurrent;
		this.calculator = new ProgressCalculator(now, progressRemaining, stalledTimeWindowMs);
	}
	
	public void setProgressCount(int currentCount) {
		calculator.setProgressCount(currentCount - startProgessCount);
	}
	
	public void setProgressWhole(int wholeCount) {
		calculator.setProgressWhole(wholeCount - startProgessCount);
	}
	
	public long estimateWholeTime() {
		return calculator.estimateWholeTime() + startElapsedMs;
	}

	/**
	 * @return
	 * @see net.sf.jcprogress.ProgressCalculator#estimateEndTime()
	 */
	public long estimateEndTime() {
		return this.calculator.estimateEndTime();
	}
	
	/**
	 * @return the calculator
	 */
	public ProgressCalculator getCalculator() {
		return this.calculator;
	}
}
