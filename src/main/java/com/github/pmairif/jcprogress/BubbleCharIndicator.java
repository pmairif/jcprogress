/*
 * Copyright 2009-2019 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 *
 */
package com.github.pmairif.jcprogress;

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
