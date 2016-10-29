/*
 * ConsoleProgressBase.java
 * 
 * Copyright 2008-2009 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 * 
 * tabstop=4, charset=UTF-8
 */
package net.sf.jcprogress;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author pmairif
 */
public abstract class ConsoleProgressThreadBase extends ProgressThread {
	/**
	 * where to print the progress bar
	 */
	protected PrintStream out = null;
	
	protected CharIndicator charIndicator = null;

	private boolean showSummary = true;
	
	private boolean showPercentage = true;
	
	private boolean showCounter = false;
	
	private boolean showEndDate = true;
	
	private boolean showRemainingTime = false;
	
	private DateFormat dateFormat = DateFormat.getDateTimeInstance();
	
	private NumberFormat nfCount = NumberFormat.getInstance();
	
	private NumberFormat nfPercentage;
	
	public ConsoleProgressThreadBase(PrintStream out, ProgressStatusProvider statusTextProvider) {
		this(out, statusTextProvider, Locale.getDefault());
	}

	public ConsoleProgressThreadBase(PrintStream out, ProgressStatusProvider statusTextProvider, Locale locale) {
		super(statusTextProvider, locale);
		
		this.out = out;
		this.charIndicator = new RotCharIndicator();
		
		this.nfPercentage = NumberFormat.getInstance();
		this.nfPercentage.setMaximumFractionDigits(2);		//2 Nachkommastellen
		this.nfPercentage.setMinimumFractionDigits(2);
	}
	
	@Override
	public void afterLastInvoking() {
		if (isShowSummary()) {
			TimeDiff timeDiff = new TimeDiff(getElapsedTime());
			out.printf("\n%s: %s\n", resourceBundle.getString("took"), timeDiff.toString()); //$NON-NLS-1$ //$NON-NLS-2$
		}
		else {
			out.println("");	//new line //$NON-NLS-1$
		}
	}
	
	protected void showRemainingTime() {
		if (isShowRemainingTime()) {
			TimeDiff diff = new TimeDiff(getGuessedWholeTime() - getElapsedTime());
			diff.setSecondsFractionDigits(0);
			out.printf(" %s %s", diff.toString(), resourceBundle.getString("remaining"));  //$NON-NLS-1$//$NON-NLS-2$
		}
	}
	
	protected void showEndDate() {
		if (isShowEndDate()) {
			Date end = getGuessedEnd();
			if (end != null)
				out.printf(" %s: %s", resourceBundle.getString("finish"), dateFormat.format(end)); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
	
	protected void showStalled() {
		out.printf(" %s", resourceBundle.getString("stalled")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected void showCounter() {
		if (isShowCounter()) {
			this.out.printf(" #%s", nfCount.format( statusProvider.getCurrentProgressCount() )); //$NON-NLS-1$
	
			int whole = statusProvider.getWholeProcessCount();
			if (whole > 0)
				this.out.printf("/%s", nfCount.format(whole)); //$NON-NLS-1$
		}
	}
	
	protected void showPercentage() {
		if (isShowPercentage())
			this.out.printf(" %s%%", nfPercentage.format(getCurrentPercentage()*100)); //$NON-NLS-1$
	}
	
	protected void showStatusText() {
		String text = this.statusProvider.getProgressStatusText();
		if (text != null) {
			this.out.print(" "); //$NON-NLS-1$
			this.out.print(text);
		}
	}
	
	/**
	 * @param charIndicator The charIndicator to set.
	 */
	public void setCharIndicator(CharIndicator charIndicator) {
		this.charIndicator = charIndicator;
	}

	/**
	 * @param out The out to set.
	 */
	public void setPrintStream(PrintStream out) {
		this.out = out;
	}
	
	public boolean isShowSummary() {
		return this.showSummary;
	}

	public void setShowSummary(boolean showSummary) {
		this.showSummary = showSummary;
	}

	public boolean isShowEndDate() {
		return this.showEndDate;
	}

	public void setShowEndDate(boolean showEndDate) {
		this.showEndDate = showEndDate;
	}

	/**
	 * @return Returns the showPercentage.
	 */
	public boolean isShowPercentage() {
		return showPercentage;
	}

	/**
	 * @param showPercentage The showPercentage to set.
	 */
	public void setShowPercentage(boolean showPercentage) {
		this.showPercentage = showPercentage;
	}

	/**
	 * @return Returns the showCounter.
	 */
	public boolean isShowCounter() {
		return this.showCounter;
	}

	/**
	 * show how many items of how many are processed.
	 * @param showCounter The showCounter to set.
	 */
	public void setShowCounter(boolean showCounter) {
		this.showCounter = showCounter;
	}

	public boolean isShowRemainingTime() {
		return this.showRemainingTime;
	}

	public void setShowRemainingTime(boolean showRemainingTime) {
		this.showRemainingTime = showRemainingTime;
	}
}
