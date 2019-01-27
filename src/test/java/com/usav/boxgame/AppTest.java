package com.usav.boxgame;

import java.awt.Point;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }
    
    /**
     * @Test Тест кубика
     */
    public void testDice()
    {
    	assertTrue(isNumeric(String.valueOf(Dice.getNext())));    
    	int val = Dice.getNext();
    	assertTrue(val <= Dice.MAX_VALUE && val >= Dice.MIN_VALUE);
    }
    
    /**
     * @Test Тест корреляций боксов
     */
    public void testCorrelation()
    {
    	
    	Box box1 = new Box(new Point(10,10), 10, 10);
    	Box box2 = new Box(new Point(15,15), 10, 10);
    	
    	assertTrue(box1.isCorrelation(box2));
    	
    	box1.setPosition(new Point(999,999));
    	assertFalse(box1.isCorrelation(box2));
    	
    	box1.setPosition(new Point(10,10));
    	box2.setPosition(new Point(10,15));
    	assertTrue(box1.isCorrelation(box2));
    	
    	box1.setPosition(new Point(10,10));
    	box2.setPosition(new Point(10,21));
    	assertFalse(box1.isCorrelation(box2));    
    	
    	box1.setPosition(new Point(100,100));
    	box2.setPosition(new Point(100,95));
    	assertTrue(box1.isCorrelation(box2));   
    	
    	Box box3 = new Box(new Point(100,100), 40, 40);
    	box1.setPosition(new Point(100,120));
    	assertTrue(box3.isCorrelation(box1));    
    }
    
    /**
     * @Test Тест соединений боксов
     */
    public void testConnection()
    {
    	
    	Box box1 = new Box(new Point(10,10), 10, 10);
    	Box box2 = new Box(new Point(10,20), 10, 10);
    	    	
    	// box1 по верхней грани с box2
    	assertTrue(box1.isConnection(box2));
    	    	
    	// box2 по нижней грани с box1
    	assertTrue(box2.isConnection(box1));
    	
    	box1.setPosition(new Point(999,999));    	
    	// боксы не соединены
    	assertFalse(box1.isConnection(box2));
    	
    	box1.setPosition(new Point(10,10));
    	box2.setPosition(new Point(20,10));    	
    	// box1 по правой грани с box2
    	assertTrue(box1.isConnection(box2));
    	
    	// box2 по левой грани с box1
    	assertTrue(box2.isConnection(box1));
    	
    	Box box3 = new Box(new Point(10,10), 10, 10);
    	Box box4 = new Box(new Point(10,12), 2, 2);
    	
    	// box4 по левой грани box3 изнутри
    	assertFalse(box3.isConnection(box4));    	
    	
    }
    
    
    private boolean isNumeric(String s) {  
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
    }  
}
