/*
 * ProgressCalculator.java (jcprogress)
 *
 * Copyright 2009 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 *
 * tabstop=4, charset=UTF-8
 */
package net.sf.jcprogress;


/**
 * progress calculations
 * @author pmairif
 */
public class ProgressCalculator {
	
	private final long startTime;

	/**
	 * number of items to be processed
	 */
	private int progressWhole;
	
	/**
	 * number of items processed since start
	 */
	private int progressCount = 0;

	/**
	 * milliseconds after which stalled activity is assumed
	 */
	private int stalledTimeWindowMs;

//	/**
//	 * Logger for this class
//	 */
//	private final Log logger = LogFactory.getLog(ProgressCalculator.class);

	/**
	 * @param progressWhole
	 * 		number of items to process at all
	 * @param stalledTimeWindowMs
	 *		milliseconds after which stalled activity is assumed 
	 */
	public ProgressCalculator(int progressWhole, int stalledTimeWindowMs) {
		this(System.currentTimeMillis(), progressWhole, stalledTimeWindowMs);
	}

	/**
	 * @param startTime
	 * 		start time in system time milliseconds
	 * @param progressWhole
	 * 		number of items to process at all
	 * @param stalledTimeWindowMs
	 *		milliseconds after which stalled activity is assumed 
	 */
	public ProgressCalculator(long startTime, int progressWhole, int stalledTimeWindowMs) {
		this.startTime = startTime;
		this.progressWhole = progressWhole;
		this.stalledTimeWindowMs = stalledTimeWindowMs;
	}
	
	public double getCurrentPercentage() {
		if (progressWhole > 0)
			return (double)progressCount / progressWhole;
		return 0;
	}
	
	/**
	 * are there enough data to estimate times?
	 */
	public boolean hasData() {
		return hasData(System.currentTimeMillis());
	}

	/**
	 * are there enough data to estimate times?
	 */
	boolean hasData(long now) {
		return (progressCount > 0 || now-startTime >stalledTimeWindowMs);
	}

	public boolean isStalled() {
		return isStalled(System.currentTimeMillis());
	}
	
	boolean isStalled(long now) {
		return (now-startTime>=stalledTimeWindowMs && 0 == progressCount);
	}
	
	/**
	 * estimate the whole time
	 * 
	 * @return
	 * 		estimated whole time in milliseconds
	 */
	public long estimateWholeTime() {
		return estimateWholeTime(System.currentTimeMillis());
	}

	/**
	 * estimate the whole time
	 * 
	 * @param currentMillis
	 * 		current time in system time milliseconds
	 * @return
	 * 		estimated whole time in milliseconds
	 */
	long estimateWholeTime(long currentMillis) {
		long elapsed = currentMillis - startTime;
		double f = getCurrentPercentage();
		
		if (f > 0)
			return Math.round( elapsed / f );
		return 0;
	}
	
	/**
	 * estimate the end time
	 * 
	 * @return	end time in system time milliseconds
	 */
	public long estimateEndTime() {
		return estimateEndTime(System.currentTimeMillis());
	}
	
	/**
	 * estimate the end time
	 * 
	 * @param currentMillis
	 * 		current time in system time milliseconds
	 * @return
	 * 		end time in system time milliseconds
	 */
	long estimateEndTime(long currentMillis) {
		return startTime + estimateWholeTime(currentMillis);
	}

	/**
	 * @return the progressCount
	 */
	public int getProgressCount() {
		return this.progressCount;
	}

	/**
	 * @param progressCount the progressCount to set
	 */
	public void setProgressCount(int progressCount) {
		this.progressCount = progressCount;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return this.startTime;
	}

	/**
	 * @param progressWhole the progressWhole to set
	 */
	public void setProgressWhole(int progressWhole) {
		this.progressWhole = progressWhole;
	}
	
	/**
	 * @return the progressWhole
	 */
	public int getProgressWhole() {
		return this.progressWhole;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.progressCount;
		result = prime * result + this.progressWhole;
		result = prime * result + (int) (this.startTime ^ (this.startTime >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProgressCalculator other = (ProgressCalculator) obj;
		if (this.progressCount != other.progressCount)
			return false;
		if (this.progressWhole != other.progressWhole)
			return false;
		if (this.startTime != other.startTime)
			return false;
		return true;
	}
}
