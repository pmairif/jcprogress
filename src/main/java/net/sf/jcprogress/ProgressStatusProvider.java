/*
 * ProgressStatusTextProvider.java
 *
 * Copyright 2008 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 * 
 * tabstop=4, charset=UTF-8
 */
package net.sf.jcprogress;

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
