package com.usav.boxgame;

import java.awt.Canvas;
import java.awt.Color;

import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class App {
	private static BufferStrategy bufferStrategy;
	private static Graphics graphics;
	private static Canvas canvas = new Canvas();
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

		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				new Runnable() {
					public void run() {
						Point mousePoint = new Point(e.getX(), e.getY());
						game.mouseClicked(mousePoint);
					}
				}.run();

			}
		});

		boolean running = true;
		boolean gameOver = false;

		while (running) {
			int mouse_x = MouseInfo.getPointerInfo().getLocation().x - canvas.getLocationOnScreen().x;
			int mouse_y = MouseInfo.getPointerInfo().getLocation().y - canvas.getLocationOnScreen().y;
			gameOver = !game.gameTick(new Point(mouse_x, mouse_y));

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

			game.getBoxGreen().drawBox(graphics, game.isCanBeConnected());

			bufferStrategy.show();
			graphics.dispose();
		}
	}
}
