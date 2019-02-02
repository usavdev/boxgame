package com.usav.boxgame;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game {
	private CopyOnWriteArrayList<Box> savedBoxes = new CopyOnWriteArrayList<Box>();
	public CopyOnWriteArrayList<Box> getSavedBoxes() {
		return savedBoxes;
	}

	public void setSavedBoxes(CopyOnWriteArrayList<Box> savedBoxes) {
		this.savedBoxes = savedBoxes;
	}

	private Box boxGreen;
	public Box getBoxGreen() {
		return boxGreen;
	}

	public void setBoxGreen(Box boxGreen) {
		this.boxGreen = boxGreen;
	}

	private Area area = new Area(20, 20, 19);
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	
	private boolean canBeConnected = false;
	public boolean isCanBeConnected() {
		return canBeConnected;
	}
	
	Game() {
		int leftDice = Dice.getNext();
		int rightDice = Dice.getNext();
		boxGreen = new Box(new Point(0, 0), leftDice * area.getCell(), rightDice * area.getCell());
		boxGreen.setBoxColor(new Color((int) (Math.random() * 0x1000000)));
		boxGreen.setBoxScore(leftDice * rightDice);
	}

	public void mouseClicked(MouseEvent e) {
		if (canBeConnected) {
			Box saveBox = new Box(boxGreen);
			savedBoxes.add(saveBox);
			
			boxGreen.setBoxColor(new Color((int) (Math.random() * 0x1000000)));
			int leftDice = Dice.getNext();
			int rightDice = Dice.getNext();
			boxGreen.setBoxWidth(area.getCell() * leftDice);
			boxGreen.setBoxHeigth(area.getCell() * rightDice);
			boxGreen.setBoxScore(leftDice * rightDice);
		}
	}

	public boolean gameTick(Point mousePoint) {
		boolean continueGame = true;
		
		Point2D possiblePoint = area.canBeAdded(boxGreen, savedBoxes);
		if (possiblePoint.getX() < 0) {
			continueGame = false;
		}

		int mouse_x = mousePoint.x;
		int mouse_y = mousePoint.y;

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

		Point2D newPosition = new Point(mouse_x, mouse_y);
		//newPosition = possiblePoint;
		Point2D oldPosition = boxGreen.getPosition();
		boxGreen.setPosition(area.getPosition(newPosition));
		if (!area.isContains(boxGreen)) {
			boxGreen.setPosition(oldPosition);
		}
		return continueGame;

	}

}
