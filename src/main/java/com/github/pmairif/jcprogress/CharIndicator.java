/*
 * Copyright 2009-2019 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 *
 */
package com.github.pmairif.jcprogress;

public abstract class CharIndicator {

	protected int rot = 0;

	public CharIndicator() {
		//
	}

	public abstract char getNextChar();
	
	public char getNextChar(String chars) {
		this.rot = (this.rot+1) % chars.length();
		return chars.charAt(this.rot);
	}

}
