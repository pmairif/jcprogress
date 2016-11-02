/*
 * RotCharIndicator.java
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
public class RotCharIndicator extends CharIndicator {

	private static final String rotChars = "|/-\\"; //$NON-NLS-1$

	public RotCharIndicator() {
		super();
	}

	@Override
	public char getNextChar() {
		return getNextChar(rotChars);
	}

}
