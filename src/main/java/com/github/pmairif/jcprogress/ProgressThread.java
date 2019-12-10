/*
 * Copyright 2009-2019 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 *
 */
package com.github.pmairif.jcprogress;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * abstract base class for progress indicators
 *
 * @author pmairif
 */
public abstract class ProgressThread extends Thread {
	protected ProgressStatusProvider statusProvider = null;

	protected ResourceBundle resourceBundle = null;
	
	private ProgressCalculator wholeProgess;
	
	private Checkpoint checkpoint = null;
	
	private Checkpoint secondCheckpoint = null;
	
	private long checkpointSwitchTime = 0;
	
//	/**
//	 * elapsed time in milliseconds, when checkpoint was reached
//	 */
//	private long checkpointElapsedMs = 0;
//	
//	/**
//	 * progress count since last checkpoint
//	 */
//	private int checkpointProgessCount = 0;
	
	/**
	 * time window used to guess the time needed to do the whole job
	 */
	private int timeCalcWindowMs = 10000;	//10s
	
	/**
	 * milliseconds after which stalled activity is assumed
	 */
	private int stalledTimeWindowMs = 500;	//0.5s
	
	/**
	 * last update of the indicator
	 */
	private long lastIndicatorUpdate;
	
	/**
	 * update interval of the indicator in milli seconds.
	 * default is 100ms=0.1s.
	 */
	private int indicatorUpdateIntervalMs = 100;
	
	/**
	 * last update of calculated informations
	 */
	private long lastInfoUpdate;
	
	/**
	 * update interval of the calculated information in milliseconds
	 * default is 200ms=0.2s.
	 */
	private int infoUpdateIntervalMs = 200; 
	
	private long guessedEnd = 0;
	
	private long guessedWholeTime = 0;
	
	private boolean askedToStop = false;
	
	/**
	 * Logger for this class
	 */
	private final Log logger = LogFactory.getLog(ProgressThread.class);

	/**
	 * create new progress indicator. 
	 * @param statusProvider	ProgressStatusProvider to use
	 */
	public ProgressThread(ProgressStatusProvider statusProvider) {
		this(statusProvider, Locale.getDefault());
	}

	/**
	 * create new progress indicator. 
	 * @param statusProvider	ProgressStatusProvider to use
	 * @param locale			preferred locale
	 */
	public ProgressThread(ProgressStatusProvider statusProvider, Locale locale) {
		this.statusProvider = statusProvider;
		
		long now = System.currentTimeMillis();				//memorize current time
		int wholeCount = getWholeCount();
		
		checkpointSwitchTime = now;
		checkpoint = new Checkpoint(wholeCount, 0, 0, stalledTimeWindowMs, now);
		secondCheckpoint = checkpoint;
		wholeProgess = new ProgressCalculator(now, wholeCount, stalledTimeWindowMs);
		
		lastIndicatorUpdate = now;
		lastInfoUpdate = now;
		
		this.resourceBundle = ResourceBundle.getBundle("com.github.pmairif.jcprogress.resources.ProgressResources", locale); //$NON-NLS-1$
	}

	/**
	 * time window used to guess the time needed to do the whole job
	 * @param timeCalcWindowMs the timeCalcWindowMs to set
	 */
	public void setTimeCalcWindowMs(int timeCalcWindowMs) {
		this.timeCalcWindowMs = timeCalcWindowMs;
	}
	
	/**
	 * time window used to guess the time needed to do the whole job
	 * @return the timeCalcWindowMs
	 */
	public int getTimeCalcWindowMs() {
		return this.timeCalcWindowMs;
	}
	
	/**
	 * milliseconds after which stalled activity is assumed
	 * @param stalledTimeWindowMs the stalledTimeWindowMs to set
	 */
	public void setStalledTimeWindowMs(int stalledTimeWindowMs) {
		this.stalledTimeWindowMs = stalledTimeWindowMs;
	}
	
	/**
	 * milliseconds after which stalled activity is assumed
	 * @return the stalledTimeWindowMs
	 */
	public int getStalledTimeWindowMs() {
		return this.stalledTimeWindowMs;
	}
	
	@Override
	public void run() {
		if (!System.getProperty("net.jcprogress.disable", "false").equalsIgnoreCase("true")) {
			try {
				while (!askedToStop) {
					update(false);
					sleep(100);
				}
				
				update(true);			//chance to show 100%, if finished
			}
			catch (InterruptedException e) {
				logger.error("run() - interrupted: "+e.getMessage()); //$NON-NLS-1$
			}
			
			afterLastInvoking();
		}
	}
	
	/**
	 * update the progress bar.
	 * 
	 * call this method regularly, if you don't use it as thread.
	 */
	public void update() {
		update(false);
	}
	
	/**
	 * update the progress bar.
	 * 
	 * call this method regularly, if you don't use it as thread.
	 * 
	 * @param force true, to force updating
	 */
	public void update(boolean force) {
		long now = System.currentTimeMillis();
		
		//update calculated progress information
		if (force || now - lastInfoUpdate > infoUpdateIntervalMs) {
			lastInfoUpdate = now;
			
			progressCalculations();
		}
		
		//update indicator
		if (force || now - lastIndicatorUpdate > indicatorUpdateIntervalMs) {
			lastIndicatorUpdate = now;
			show();
		}
	}
	
	/**
	 * show/update progress indicator
	 */
	abstract protected void show();

	/**
	 * perhaps there is sth. to do after the last invoking (in stop or 100%)
	 */
	abstract public void afterLastInvoking();
	
	public boolean isStalled() {
		return checkpoint.getCalculator().isStalled();
	}
	
	public boolean hasData() {
		return checkpoint.getCalculator().hasData();
	}
	
	/**
	 * @return Returns the elapsedTime.
	 */
	public long getElapsedTime() {
		return System.currentTimeMillis() - wholeProgess.getStartTime();
	}

	/**
	 * @return Returns the guessedEnd.
	 */
	public Date getGuessedEnd() {
		return new Date(guessedEnd);
	}

	/**
	 * @return Returns the guessedWholeTime.
	 */
	public long getGuessedWholeTime() {
		return guessedWholeTime;
	}

	/**
	 * @return Returns the start.
	 */
	public Date getStart() {
		return new Date(wholeProgess.getStartTime());
	}

	/**
	 * @return Returns the wholeCount.
	 */
	public int getWholeCount() {
		if (statusProvider != null)
			return statusProvider.getWholeProcessCount();
		
		return 0;
	}

	int getCurrentCount() {
		if (statusProvider != null)
			return statusProvider.getCurrentProgressCount();
		
		return 0;
	}

	/**
	 * ask to stop and return immediately
	 */
	public void askToStop() {
		this.askedToStop = true;
	}
	
	/**
	 * ask to stop and wait until stopped.
	 */
	public void waitToStop() {
		try {
			askToStop();
			while (isAlive()) {
				sleep(100);
			}
		}
		catch (InterruptedException e) {
			logger.error("waitToStop() - interrupted exception: "+e.getMessage()); //$NON-NLS-1$
		}
	}

	/**
	 * get the update interval of the indicator in milli seconds.
	 */
	public int getIndicatorUpdateIntervalMs() {
		return this.indicatorUpdateIntervalMs;
	}

	/**
	 * set the update interval of the indicator in milliseconds.
	 */
	public void setIndicatorUpdateIntervalMs(int indicatorUpdateIntervalMs) {
		this.indicatorUpdateIntervalMs = indicatorUpdateIntervalMs;
	}

	/**
	 * get the update interval of the calculated information in milli seconds
	 */
	public int getInfoUpdateIntervalMs() {
		return this.infoUpdateIntervalMs;
	}

	/**
	 * set the update interval of the calculated information in milli seconds
	 */
	public void setInfoUpdateIntervalMs(int infoUpdateIntervalMs) {
		this.infoUpdateIntervalMs = infoUpdateIntervalMs;
	}

	public float getCurrentPercentage() {
		return (float) wholeProgess.getCurrentPercentage();
	}

	/**
	 * progress calculations
	 */
	protected void progressCalculations() {
		int wholeCount = getWholeCount();
		int currentCount = getCurrentCount();
		
		wholeProgess.setProgressCount(currentCount);
		wholeProgess.setProgressWhole(wholeCount);
		
		checkpoint.setProgressCount(currentCount);
		checkpoint.setProgressWhole(wholeCount);
		
		secondCheckpoint.setProgressCount(currentCount);
		secondCheckpoint.setProgressWhole(wholeCount);
		
		guessedEnd = checkpoint.estimateEndTime();
		guessedWholeTime = checkpoint.estimateWholeTime();
		
		long now = System.currentTimeMillis();
		if (now-checkpointSwitchTime >= timeCalcWindowMs) {
			//use two shifted checkpoints to avoid starting without data on switch
			checkpoint = secondCheckpoint;
			secondCheckpoint = new Checkpoint(wholeCount, currentCount, now-wholeProgess.getStartTime(), stalledTimeWindowMs, now);
			checkpointSwitchTime = now;
		}
	}
}	
