package com.usav.boxgame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Hello world!
 *
 */
public class App {
	private static ArrayList<Box> savedBoxes = new ArrayList<Box>();
	private static Box boxGreen = new Box(new Point(1, 1), 100, 100);
	private static BufferStrategy bufferStrategy;
	private static Graphics graphics;
	private static Canvas canvas = new Canvas();
	private static boolean canBeConnected = false;

	public static void main(String[] args) {
		System.out.println("Hello World!");

		for (int i = 0; i < 5; i++) {
			System.out.println(Dice.getValue());
		}

		DrawTest();

	}

	public static void DrawTest() {
		
		final String title = "Test Window";
		final int width = 1200;
		final int height = width / 16 * 9;

		// Creating the frame.
		JFrame frame = new JFrame(title);

		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		canvas.setSize(width, height);
		canvas.setBackground(Color.BLACK);
		canvas.setVisible(true);
		canvas.setFocusable(false);

		// Putting it all together.
		frame.add(canvas);

		canvas.createBufferStrategy(3);

		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (canBeConnected) {
					savedBoxes.add(new Box(boxGreen.getPosition(), boxGreen.getBoxWidth(), boxGreen.getBoxHeigth()));

				}
			}
		});
		
		Area area = new Area(60, 30, 20);

		boolean running = true;

		while (running) {

			int mouse_x = MouseInfo.getPointerInfo().getLocation().x - canvas.getLocationOnScreen().x;
			int mouse_y = MouseInfo.getPointerInfo().getLocation().y - canvas.getLocationOnScreen().y;
			canBeConnected = savedBoxes.size() == 0;
			
			if (!canBeConnected) {
				for (Box box : savedBoxes) {
					if (box.isConnection(boxGreen)) {
						canBeConnected = true;						
					}
				}
			}			

			bufferStrategy = canvas.getBufferStrategy();
			graphics = bufferStrategy.getDrawGraphics();
			graphics.clearRect(0, 0, width, height);
			
			area.drawArea(graphics);

			graphics.setColor(Color.YELLOW);
			graphics.drawString("canBeConnected: " + String.valueOf(canBeConnected), 5, 15);
					
			graphics.setColor(Color.RED);
			Iterator<Box> it = savedBoxes.iterator(); 
			while(it.hasNext()) {
				Box box = it.next();
				box.drawBox(graphics);
			}


			if (canBeConnected) {
				graphics.setColor(Color.GREEN);
			} else {
				graphics.setColor(Color.YELLOW);
			}
			boxGreen.setPosition(area.getPosition(new Point(mouse_x, mouse_y)));
			boxGreen.drawBox(graphics);

			bufferStrategy.show();
			graphics.dispose();
		}
	}
}
