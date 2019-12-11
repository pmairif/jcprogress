/*
 * Copyright 2009-2019 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 *
 */
package com.github.pmairif.jcprogress;

import java.io.PrintStream;
import java.util.Locale;

public class ConsoleProgressBarThread extends ConsoleProgressThreadBase {
	/**
	 * create new progress bar and set the counter to be reached. 
	 */
	public ConsoleProgressBarThread(PrintStream out, ProgressStatusProvider statusTextProvider, Locale locale) {
		super(out, statusTextProvider, locale);
		
		this.barSize = 40;
	}

	public ConsoleProgressBarThread(PrintStream out, ProgressStatusProvider statusTextProvider) {
		this(out, statusTextProvider, Locale.getDefault());
	}

	/**
	 * progress bar on console.
	 */
	@Override
	protected void show() {
		if (statusProvider != null) {
			out.print("\r");				//go to begin of line //$NON-NLS-1$
			showProgressBar();
			showPercentage();
			showCounter();
			showStatusText();
			
			if (hasData()) {
				if (isStalled()) {
					showStalled();
				}
				else {
					showEndDate();
					showRemainingTime();
				}
			}
		}
	}

	protected void showProgressBar() {
		float percentage = getCurrentPercentage();
		if (percentage > 1)
			percentage = 1;
		int chars = (int) (percentage * barSize);	//Anzahl Zeichen auf Balken

		out.print("["); //$NON-NLS-1$
		for (int i=0; i<chars; i++) {
			out.print("#");			 //$NON-NLS-1$
		}
	
		if (chars < barSize)
			out.print(charIndicator.getNextChar());
	
		for (int i=chars+1; i<barSize; i++) {
			out.print(".");			 //$NON-NLS-1$
		}
		out.print("]"); //$NON-NLS-1$
	}
	
	/**
	 * resize the bar length
	 * 
	 * @param b
	 * 		number of characters for the bar
	 */
	public void setSize(int b) {
		barSize = b;
	}
	
	/**
	 * Balkenlänge
	 */
	private int barSize;
}
