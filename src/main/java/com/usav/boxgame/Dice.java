package com.usav.boxgame;

/**
 * @author usav
 *
 */
public class Dice {
	
	static final int MIN_VALUE = 1; 
	static final int MAX_VALUE = 6; 
	
	public static int getValue() {
		return randomWithRange(MIN_VALUE,MAX_VALUE);
	}

	private static int randomWithRange(int min, int max)
	{
	   int range = Math.abs(max - min) + 1;     
	   return (int)(Math.random() * range) + (min <= max ? min : max);
	}
	
}
