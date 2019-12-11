/*
 * Copyright 2009-2019 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 *
 */
package com.github.pmairif.jcprogress;

/**
 * provide progress indicator with up to date status information
 */
public interface ProgressStatusProvider {
	String getProgressStatusText();
	
	int getCurrentProgressCount();
	
	int getWholeProcessCount();
}
