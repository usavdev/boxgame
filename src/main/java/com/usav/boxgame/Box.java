/**
 * 
 */
package com.usav.boxgame;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * @author usav
 *
 */
public class Box {

	private Rectangle2D rect = new Rectangle2D.Double();
	private ArrayList<Point2D> coords = new ArrayList<Point2D>();
	private Point2D position;
	private int boxWidth;
	private int boxHeigth;

	/**
	 * @param rect
	 *            the rect to set
	 */
	private final void setRect() {
		rect.setRect(this.getPosition().getX(), this.getPosition().getY(), this.getBoxWidth(), this.getBoxHeigth());
	}

	/**
	 * @return the coords
	 */
	public final ArrayList<Point2D> getCoords() {
		return coords;
	}

	/**
	 * @param calc the coords to set
	 */
	public final void setCoords() {
		
		// Левая верхняя (0)
		coords.get(0).setLocation(this.getRect().getMinX(), this.getRect().getMinY());
		
		// Правая верхняя (1)
		coords.get(1).setLocation(this.getRect().getMaxX(), this.getRect().getMinY());
		
		// Правая нижняя (2)
				coords.get(2).setLocation(this.getRect().getMaxX(), this.getRect().getMaxY());
		
		// Левая нижняя (3)
		coords.get(3).setLocation(this.getRect().getMinX(), this.getRect().getMaxY());

	}

	/**
	 * @return the rect
	 */
	public final Rectangle2D getRect() {
		return rect;
	}

	

	/**
	 * @return the position
	 */
	public final Point2D getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public final void setPosition(Point2D position) {
		this.position = position;	
		setRect();
		setCoords();
	}

	/**
	 * @return the boxWidth
	 */
	public final int getBoxWidth() {
		return boxWidth;
	}

	/**
	 * @param boxWidth
	 *            the boxWidth to set
	 */
	public final void setBoxWidth(int boxWidth) {
		this.boxWidth = boxWidth;
		setRect();
		setCoords();
	}

	/**
	 * @return the boxHeigth
	 */
	public final int getBoxHeigth() {
		return boxHeigth;
	}

	/**
	 * @param boxHeigth
	 *            the boxHeigth to set
	 */
	public final void setBoxHeigth(int boxHeigth) {
		this.boxHeigth = boxHeigth;
		setRect();
		setCoords();
	}

	/**
	 * Create box
	 */
	public Box(Point2D startPosition, int width, int height) {
		for (int i = 0; i < 4; i++) {
			coords.add(new Point2D.Double());
		}

		setPosition(startPosition);
		setBoxWidth(width);
		setBoxHeigth(height);
		setCoords();
		setRect();
	}

	public boolean isCorrelation(Box anotherBox) {
		ArrayList<Point2D> coords = this.getCoords();

		for (Point2D point : coords) {

			if (anotherBox.getRect().contains(point)) {
				return true;
			}
		}

		return false;
	}

	public boolean isConnection(Box anotherBox) {

		ArrayList<Point2D> coords = anotherBox.getCoords();
		Line2D line = new Line2D.Double();
	
		for (Point2D point : coords) {
			
			line.setLine(this.getCoords().get(0), this.getCoords().get(1));
			if (line.ptLineDist(point) == 0) {
				return true;
			}
			line.setLine(this.getCoords().get(1), this.getCoords().get(2));
			if (line.ptLineDist(point) == 0) {
				return true;
			}
			line.setLine(this.getCoords().get(2), this.getCoords().get(3));
			if (line.ptLineDist(point) == 0) {
				return true;
			}
			line.setLine(this.getCoords().get(3), this.getCoords().get(0));
			if (line.ptLineDist(point) == 0) {
				return true;
			}
		}

		return false;
	}

	public void drawBox(Graphics graphics) {
		graphics.drawRect((int) this.getPosition().getX(), (int) this.getPosition().getY(),
				this.getBoxWidth(), this.getBoxHeigth());
	}

}
