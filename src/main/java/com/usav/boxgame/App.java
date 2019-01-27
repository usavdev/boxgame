package com.usav.boxgame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

public class App {
	private static ArrayList<Box> savedBoxes = new ArrayList<Box>();
	private static Area area = new Area(20, 20, 19);
	private static Box boxGreen = new Box(new Point(-999, -999), Dice.getNext() * area.getCell(),
			Dice.getNext() * area.getCell());
	private static BufferStrategy bufferStrategy;
	private static Graphics graphics;
	private static Canvas canvas = new Canvas();
	private static boolean canBeConnected = false;

	public static void main(String[] args) {
		System.out.println("Hello Box!");

		DrawTest();		

	}

	public static void DrawTest() {

		final String title = "Test Box";
		final int width = area.getAreaWidth();
		final int height = area.getAreaHeight();

		JFrame frame = new JFrame(title);

		canvas.setSize(width, height);
		canvas.setBackground(Color.BLACK);
		canvas.setVisible(true);
		canvas.setFocusable(false);

		frame.add(canvas);

		frame.pack();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		canvas.createBufferStrategy(3);
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (canBeConnected) {
					Box saveBox = new Box(boxGreen.getPosition(), boxGreen.getBoxWidth(), boxGreen.getBoxHeigth());
					saveBox.setBoxColor(boxGreen.getBoxColor());
					savedBoxes.add(saveBox);
					boxGreen.setBoxColor(new Color((int) (Math.random() * 0x1000000)));
					boxGreen.setBoxWidth(area.getCell() * Dice.getNext());
					boxGreen.setBoxHeigth(area.getCell() * Dice.getNext());
				}
			}
		});

		boxGreen.setBoxColor(new Color((int) (Math.random() * 0x1000000)));

		boolean running = true;

		while (running) {

			running = area.canBeAdded(boxGreen, savedBoxes);

			int mouse_x = MouseInfo.getPointerInfo().getLocation().x - canvas.getLocationOnScreen().x;
			int mouse_y = MouseInfo.getPointerInfo().getLocation().y - canvas.getLocationOnScreen().y;
			canBeConnected = savedBoxes.size() == 0;

			if (!canBeConnected) {
				for (Box box : savedBoxes) {
					if (boxGreen.isConnection(box)) {
						canBeConnected = true;
						for (Box boxCorr : savedBoxes) {
							if (boxGreen.isCorrelation(boxCorr)) {
								canBeConnected = false;
								break;
							}
						}
					}
				}
			}

			bufferStrategy = canvas.getBufferStrategy();
			graphics = bufferStrategy.getDrawGraphics();
			graphics.clearRect(0, 0, width, height);

			area.drawArea(graphics);

			for (Box box : savedBoxes) {
				box.drawBox(graphics);
			}

			Point2D newPosition = new Point(mouse_x - boxGreen.getBoxWidth() / 2,
					mouse_y - boxGreen.getBoxHeigth() / 2);
			Point2D oldPosition = boxGreen.getPosition();
			boxGreen.setPosition(area.getPosition(newPosition));
			if (!area.isContains(boxGreen)) {
				boxGreen.setPosition(oldPosition);
			}

			boxGreen.drawBox(graphics, canBeConnected);

			bufferStrategy.show();
			graphics.dispose();
		}
	}
}
