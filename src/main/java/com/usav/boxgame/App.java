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
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;

public class App {
	private static CopyOnWriteArrayList<Box> savedBoxes = new CopyOnWriteArrayList<Box>();
	private static Area area = new Area(20, 20, 19);
	private static Box boxGreen;
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

		int leftDice = Dice.getNext();
		int rightDice = Dice.getNext();
		boxGreen = new Box(new Point(0, 0), leftDice * area.getCell(), rightDice * area.getCell());
		boxGreen.setBoxColor(new Color((int) (Math.random() * 0x1000000)));
		boxGreen.setBoxScore(leftDice * rightDice);

		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (canBeConnected) {
					Box saveBox = new Box(boxGreen.getPosition(), boxGreen.getBoxWidth(), boxGreen.getBoxHeigth());
					saveBox.setBoxColor(boxGreen.getBoxColor());
					saveBox.setBoxScore(boxGreen.getBoxScore());
					savedBoxes.add(saveBox);
					boxGreen.setBoxColor(new Color((int) (Math.random() * 0x1000000)));
					int leftDice = Dice.getNext();
					int rightDice = Dice.getNext();
					boxGreen.setBoxWidth(area.getCell() * leftDice);
					boxGreen.setBoxHeigth(area.getCell() * rightDice);
					boxGreen.setBoxScore(leftDice * rightDice);
				}
			}
		});

		boolean running = true;
		boolean gameOver = !(area.canBeAdded(boxGreen, savedBoxes).getX() >= 0);
		while (running) {
			Point2D possiblePoint = area.canBeAdded(boxGreen, savedBoxes);
			if (!gameOver) {
				gameOver = !(possiblePoint.getX() >= 0);
			} else {
				frame.setTitle(title + " - Игра окончена!");
			}

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
			//newPosition = possiblePoint;
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
