package com.usav.boxgame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        for (int i = 0; i < 5; i++) {
        	System.out.println(Dice.getValue());
        }
        
        DrawTest();
        
    }
    
    public static void DrawTest() {
    	final String title = "Test Window";
        final int width = 1200;
        final int height = width / 16 * 9;

        //Creating the frame.
        JFrame frame = new JFrame(title);

        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        //Creating the canvas.
        Canvas canvas = new Canvas();

        canvas.setSize(width, height);
        canvas.setBackground(Color.BLACK);
        canvas.setVisible(true);
        canvas.setFocusable(false);

        //Putting it all together.
        frame.add(canvas);

        canvas.createBufferStrategy(3);

        boolean running = true;

        BufferStrategy bufferStrategy;
        Graphics graphics;
        
        Box boxGreen = new Box(new Point(1,1), 100, 100);
        Box boxRed = new Box(new Point(200,200), 200, 200);

        while (running) {
            bufferStrategy = canvas.getBufferStrategy();
            graphics = bufferStrategy.getDrawGraphics();
            graphics.clearRect(0, 0, width, height);

            graphics.setColor(Color.YELLOW);
            int mouse_x=MouseInfo.getPointerInfo().getLocation().x-canvas.getLocationOnScreen().x;
            int mouse_y=MouseInfo.getPointerInfo().getLocation().y-canvas.getLocationOnScreen().y;
            graphics.drawString("Correlation: " + String.valueOf(boxGreen.isCorrelation(boxRed)), 5, 15);
            graphics.drawString("Connection: " + String.valueOf(boxGreen.isConnection(boxRed)), 5, 35);
            graphics.drawString("x: " + String.valueOf(boxGreen.getPosition().getX()), 5, 55);
            graphics.drawString("y: " + String.valueOf(boxGreen.getPosition().getY()), 5, 75);

                                    
            graphics.setColor(Color.RED);
            boxRed.drawBox(graphics);
            
            graphics.setColor(Color.GREEN);
            boxGreen.setPosition(new Point(mouse_x,mouse_y));
            boxGreen.drawBox(graphics);

            bufferStrategy.show();
            graphics.dispose();
        }
	}
}
