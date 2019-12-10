/*
 * Copyright 2009-2019 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 *
 */
package com.github.pmairif.jcprogress;

/**
 * @author pmairif
 */
public class CounterClockwiseRotCharIndicator extends CharIndicator {

	private static final String rotChars = "\\-/|"; //$NON-NLS-1$

	public CounterClockwiseRotCharIndicator() {
		super();
	}

	@Override
	public char getNextChar() {
		return getNextChar(rotChars);
	}

}