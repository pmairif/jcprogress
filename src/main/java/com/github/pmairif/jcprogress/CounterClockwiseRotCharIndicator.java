/*
 * Copyright 2009-2019 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 *
 */
package com.github.pmairif.jcprogress;

public class CounterClockwiseRotCharIndicator extends CharIndicator {

	private static final String ROT_CHARS = "\\-/|"; //$NON-NLS-1$

	public CounterClockwiseRotCharIndicator() {
		super();
	}

	@Override
	public char getNextChar() {
		return getNextChar(ROT_CHARS);
	}

}
