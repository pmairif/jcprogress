/*
 * Copyright 2009-2019 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 *
 */
package com.github.pmairif.jcprogress;

import java.io.PrintStream;

/**
 * no progress bar, just indicate that still sth. is happening
 * @author pmairif
 */
public class ConsoleProgressIndicatorThread extends ConsoleProgressThreadBase {

	public ConsoleProgressIndicatorThread(PrintStream out, ProgressStatusProvider statusTextProvider) {
		super(out, statusTextProvider);
	}

	@Override
	protected void show() {
		if (statusProvider != null) {
			progressCalculations();

			this.out.print("\r");				//Anfang der Zeile //$NON-NLS-1$
			this.out.print(charIndicator.getNextChar());
		
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
}
