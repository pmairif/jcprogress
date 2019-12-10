/*
 * Copyright 2009-2019 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 *
 */
package com.github.pmairif.jcprogress;

/**
 * provide progress indicator with up to date status information
 * @author pmairif
 * @since 1.0
 */
public interface ProgressStatusProvider {
	public String getProgressStatusText();
	
	public int getCurrentProgressCount();
	
	public int getWholeProcessCount();
}
