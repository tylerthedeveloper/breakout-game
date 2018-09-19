package breakout.ScreenElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import breakout.Constants;

public abstract class ScreenElement extends JComponent {

	/************************
	 * Member Variables *
	 *************************/

	protected Rectangle2D elementBounds;

	private Point Location;
	private Color fillColor;
	private Dimensions dimensions;

	/************************
	 * Public Functions *
	 *************************/

	// For rectangular shapes
	protected ScreenElement(Color fillColor, int x, int y, int width, int height) {
		this.setFillColor(fillColor);
		this.Location = new Point(x, y);
		this.dimensions = new Dimensions(width, height);
	}

	protected ScreenElement(Color fillColor, int x, int y, int radius) {
		this.setFillColor(fillColor);
		this.Location = new Point(x, y);
		this.dimensions = new Dimensions(radius);
	}

	public void updateLocation(int x_offset, int y_offset) {
		this.Location.x += x_offset;
		this.Location.y += y_offset;
	}

	protected boolean canMoveLeft(ScreenElement screenElement, int leftOffset) {
		int x = screenElement.getX();
		int boardX = Constants.getBoardPosX();
		return (boardX <= (x + leftOffset));
	}

	protected boolean canMoveRight(ScreenElement screenElement, int rightOffset) {
		int x = screenElement.getX();
		int elementWidth = screenElement.getWidth();
		int boardX = Constants.getBoardPosX();
		int boardWidth = Constants.getBoardWidth();
		return ((boardX + boardWidth) >= (x + elementWidth + rightOffset));
	}

	public Rectangle2D getBounds2D() {
		return new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
	}

	/************************
	 * Getters + Setters *
	 *************************/

	protected void setFillColor(Color c) {
		this.fillColor = c;
	}

	protected Color getFillColor() {
		return this.fillColor;
	}

	public Point getLocation() {
		return this.Location;
	}

	public int getX() {
		return getLocation().x;
	}

	public int getY() {
		return getLocation().y;
	}

	public int getWidth() {
		return this.dimensions.getWidth();
	}

	public int getHeight() {
		return this.dimensions.getHeight();
	}

	public int getBottomYCoordinates() {
		return this.getY() + this.getHeight();
	}

	public int getRightXCoordinates() {
		return this.getX() + this.getWidth();
	}

	public void setPosition(int positionX, int positionY) {
		this.Location.x = positionX;
		this.Location.y = positionY;
	}

	/************************
	 * Abstract methods *
	 *************************/

	public abstract void draw(Graphics g);
	public abstract void reset();

	/************************
	 * Helper Class *
	 *************************/

	class Dimensions {

		private int width;
		private int height;

		protected Dimensions(int width, int height) {
			this.width = width;
			this.height = height;
		}

		protected Dimensions(int radius) {
			this.width = radius;
		}

		protected int getWidth() {
			return this.width;
		}

		protected int getHeight() {
			return this.height;
		}
	}
}
