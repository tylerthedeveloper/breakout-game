package breakout.ScreenElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import breakout.Constants;

public class Ball extends ScreenElement implements Sprite {
		
	protected int positionX; 
	protected int positionY;
	protected int radius;
	
	protected int SpeedX = Constants.getBallSpeedX();
	protected int SpeedY = Constants.getBallSpeedY();
	
	public Ball(Color fillColor, int x, int y, int radius) {
		super(fillColor, x, y, radius);
		positionX = x;
		positionY = y;
		this.radius = radius;
	}
	
	public void setSpeedX(int speedX) {
		SpeedX = speedX;
	}

	public void setSpeedY(int speedY) {
		SpeedY = speedY;
	}
	
	public int getSpeedX() {
		return SpeedX;
	}
	
	public int getSpeedY() {
		return SpeedY;
	}

	public int getBottomCoordinates() {
		return getY() + getHeight();
	}
	
	public int getRightCoordinates() {
		return getX() + getWidth();
	}
	
	public int getYRadiusCoordinates() {
		return getY() + (getHeight()/2);
	}

	public int getXRadiusCoordinates() {
		return getX() + (getWidth()/2);
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// TODO: WHAT THE HELL IS THIS
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Ellipse2D.Double ball = new Ellipse2D.Double(getX(), getY(), radius, radius);
		elementBounds = ball.getBounds();
		g2.setPaint(getFillColor());
		g2.fill(ball);
		
	}
	
	public Ellipse2D getBounds2DBall() {
		return new Ellipse2D.Double(getX(), getY(), getWidth(), getWidth());
	}

	// TODO: CONFIRM THIS IS OKAY TO BE REMOVED
//	public void moveBall(int newSpeedX, int newSpeedY) {
//		this.setSpeedX(newSpeedX);
//		this.setSpeedY(newSpeedY);
//	}	
	
	@Override
	public void update() {
//		updateLocation(this.getSpeedX(), this.getSpeedY());
	}

	public void updateLocation() {
		updateLocation(this.getSpeedX(), this.getSpeedY());
	}

	public void revertLocation(int oldPositionX, int oldPositionY) {
		setLocation(oldPositionX, oldPositionY);
	}

	@Override
	public void reset() {
		this.setPosition(Constants.getBallPosX(), Constants.getBallPosY());
	}

}
