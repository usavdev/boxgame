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
	private static Game game;

	public static void main(String[] args) {
		System.out.println("Hello Box!");
		
		game = new Game();
		
		DrawTest();

	}

	public static void DrawTest() {

		final String title = "Test Box";
		final int width = game.getArea().getAreaWidth();
		final int height = game.getArea().getAreaHeight();

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
				game.mouseClicked(e);
			}
		});

		boolean running = true;
		boolean gameOver = false;
		
		while (running) {
			int mouse_x = MouseInfo.getPointerInfo().getLocation().x - canvas.getLocationOnScreen().x;
			int mouse_y = MouseInfo.getPointerInfo().getLocation().y - canvas.getLocationOnScreen().y;
			gameOver = !game.gameTick(new Point(mouse_x,mouse_y));
			
			if (gameOver) {
				frame.setTitle(title + " - Игра окончена!");
			}

			bufferStrategy = canvas.getBufferStrategy();
			graphics = bufferStrategy.getDrawGraphics();
			graphics.clearRect(0, 0, width, height);

			game.getArea().drawArea(graphics);

			for (Box box : game.getSavedBoxes()) {
				box.drawBox(graphics);
			}

			game.getBoxGreen().drawBox(graphics,game.isCanBeConnected());
			
			bufferStrategy.show();
			graphics.dispose();
		}
	}
}
