/*
 * Copyright 2009-2019 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 *
 */
package com.github.pmairif.jcprogress;

/**
 * @author pmairif
 */
public abstract class CharIndicator {

	protected int rot = 0;

	public CharIndicator() {
		//
	}

	abstract public char getNextChar();
	
	public char getNextChar(String chars) {
		this.rot = (this.rot+1) % chars.length();
		return chars.charAt(this.rot);
	}

}
