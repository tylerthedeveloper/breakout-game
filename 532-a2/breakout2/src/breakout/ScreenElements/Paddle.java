package breakout.ScreenElements;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import breakout.Constants;

public class Paddle extends ScreenElement {
	
	
	public Paddle(Color fillColor, int x, int y, int width, int height) {
		super(fillColor, x, y, width, height);
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(getFillColor());
		g2.fill(new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight()));
	}
	
	public Rectangle2D getBounds2D() {
		return new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
	}
	
	public void collisionResponse(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT && canMoveLeft(this, Constants.getPaddleLeftOffset()))
			updateLocation(Constants.getPaddleLeftOffset(), 0);
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && canMoveRight(this, Constants.getPaddleRightOffset()))
			updateLocation(Constants.getPaddleRightOffset(), 0);
		repaint();
	}

	@Override
	public void reset() {
		this.setPosition(Constants.getPaddlePosX(), Constants.getPaddlePosY());
	}

}
