/*
 * BubbleCharIndicator.java
 *
 * Copyright 2008 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 * 
 * tabstop=4, charset=UTF-8
 */
package net.sf.jcprogress;

/**
 * @author pmairif
 */
public class BubbleCharIndicator extends CharIndicator {

	private static final String rotChars = ".oOo"; //$NON-NLS-1$

	public BubbleCharIndicator() {
		super();
	}

	/*
	 * @see de.mairif.progress.CharIndicator#getNextChar()
	 */
	@Override
	public char getNextChar() {
		return getNextChar(rotChars);
	}
}
