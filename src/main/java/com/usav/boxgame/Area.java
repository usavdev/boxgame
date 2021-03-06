package com.usav.boxgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Area {

	private int width;
	private int height;
	private int cell;
	private Box selfBox;

	private final Color backColor = Color.WHITE;
	private final Color lineColor = Color.LIGHT_GRAY;

	public Area(int width, int height, int cell) {
		setWidth(width);
		setHeight(height);
		setCell(cell);
		selfBox = new Box(new Point2D.Double(0, 0), this.getAreaWidth(), this.getAreaHeight());
	}

	public void drawArea(Graphics graphics) {
		Color oldColor = graphics.getColor();

		graphics.setColor(backColor);

		graphics.fillRect(0, 0, this.getAreaWidth(), this.getAreaHeight());

		graphics.setColor(lineColor);
		for (int i = 0; i < this.getAreaWidth(); i += this.getCell()) {
			graphics.drawLine(i, 0, i, this.getAreaHeight());
		}
		for (int i = 0; i < this.getAreaHeight(); i += this.getCell()) {
			graphics.drawLine(0, i, this.getAreaWidth(), i);
		}

		graphics.setColor(oldColor);
	}

	/**
	 * @return the width
	 */
	public final int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	private final void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public final int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	private final void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the cell
	 */
	public final int getCell() {
		return cell;
	}

	/**
	 * @param cell the cell to set
	 */
	private final void setCell(int cell) {
		this.cell = cell;
	}

	public final int getAreaWidth() {
		return this.getWidth() * this.getCell();
	}

	public final int getAreaHeight() {
		return this.getHeight() * this.getCell();
	}

	public final Point2D getPosition(Point2D position) {
		return new Point2D.Double(Math.round((position.getX() / this.getCell())) * this.getCell(),
				Math.round((position.getY() / this.getCell())) * this.getCell());
	}

	public final boolean isContains(Box box) {
		return selfBox.isContains(box);
	}

	public final Point2D canBeAdded(Box box, CopyOnWriteArrayList<Box> savedBoxes) {
		Point2D sourcePos = box.getPosition();
		Point2D possiblePos = new Point2D.Double();
		ArrayList<Point2D> possiblePoints = new ArrayList<Point2D>();

		if (savedBoxes.size() == 0) {
			return possiblePos;
		}

		possiblePos.setLocation(-999, -999);
		for (int x = 0; x <= this.getWidth() - box.getBoxWidth() / this.getCell(); x++) {
			for (int y = 0; y <= this.getHeight() - box.getBoxHeigth() / this.getCell(); y++) {
				Point2D newPos = new Point2D.Double(x * this.getCell(), y * this.getCell());
				box.setPosition(newPos);
				svd: for (Box sBox : savedBoxes) {
					if (box.isConnection(sBox)) {
						for (Box boxCorr : savedBoxes) {
							if (box.isCorrelation(boxCorr)) {
								break svd;
							}
						}
						possiblePos = newPos;
						possiblePoints.add(possiblePos);
					}
				}
			}
		}

		box.setPosition(sourcePos);
		if (possiblePoints.size() > 0) {
			possiblePos = possiblePoints.get(ThreadLocalRandom.current().nextInt(0, possiblePoints.size()));
		}
		return possiblePos;
	}
}
