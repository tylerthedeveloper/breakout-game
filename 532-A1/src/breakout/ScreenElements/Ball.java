package breakout.ScreenElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import breakout.Constants;

public class Ball extends ScreenElement implements Observer{
		
	protected int positionX; 
	protected int positionY;
	protected int radius;
	
	protected int SpeedX = Constants.getBallSpeedX();
	protected int SpeedY = Constants.getBallSpeedY();
	
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

	public Ball(Color fillColor, int x, int y, int radius) {
		super(fillColor, x, y, radius);
		positionX = x;
		positionY = y;
		this.radius = radius;
	}

	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D) g;
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

	public void moveBall(int newSpeedX, int newSpeedY) {

		this.setSpeedX(newSpeedX);
		this.setSpeedY(newSpeedY);
	}
	
	
	
	@Override
	public void update() {
	 updateLocation(this.getSpeedX(), this.getSpeedY());
//	 System.out.println(this.getSpeedX() +" : " + this.getSpeedY());
	}


}
