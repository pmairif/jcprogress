/*
 * Copyright 2009-2019 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 *
 */
package com.github.pmairif.jcprogress;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * represents a period of time
 */
public class TimeDiff {

	private float seconds;
	private int minutes;
	private int hours;
	private int days;
	
	private int secondsFractionDigits = 3;
	
	public TimeDiff(long period) {
		this.days = (int) (period / 86400000);		// 86 400 000 = 1000ms * 60s * 60min * 24h
		this.hours = (int) (period / 3600000) % 24;	//  3 600 000 = 1000ms * 60s * 60min
		this.minutes = (int) (period / 60000) % 60;	//     60 000 = 1000ms * 60s
		this.seconds = ((period / 1000) % 60) + ((period%1000)/1000f);
	}

	/**
	 * @return the days
	 */
	public int getDays() {
		return days;
	}

	/**
	 * @return the hours
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * @return the minutes
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * @return the seconds
	 */
	public float getSeconds() {
		return seconds;
	}
	
	public int getSecondsFractionDigits() {
		return this.secondsFractionDigits;
	}

	public void setSecondsFractionDigits(int secondsFractionDigits) {
		this.secondsFractionDigits = secondsFractionDigits;
	}

	@Override
	public String toString() {
		return toString(Locale.getDefault()); 
	}

	public String toString(Locale locale) {
		StringBuilder b = new StringBuilder();
		NumberFormat nf = NumberFormat.getInstance(locale);
		nf.setMaximumFractionDigits(secondsFractionDigits);
		
		if (0==days && 0==hours && 0==minutes && 0f==seconds)
			b.append("0s"); //$NON-NLS-1$
		else {
			boolean ws = false;
			if (days>0) {
				b.append(days).append("d"); //$NON-NLS-1$
				ws = true;
			}
			if (hours>0) {
				if (ws) b.append(" "); //$NON-NLS-1$
				b.append(hours).append("h"); //$NON-NLS-1$
				ws = true;
			}
			if (minutes>0) {
				if (ws) b.append(" "); //$NON-NLS-1$
				b.append(minutes).append("min"); //$NON-NLS-1$
				ws = true;
			}
			if (seconds>0) {
				if (ws) b.append(" "); //$NON-NLS-1$
				b.append(nf.format(seconds)).append("s"); //$NON-NLS-1$
			}
		}
		
		return b.toString();
	}
}
