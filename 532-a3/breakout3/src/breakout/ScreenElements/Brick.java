package breakout.ScreenElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Brick extends ScreenElement {
	
	int brickId;
	public int getBrickId() {
		return brickId;
	}

	public void setBrickId(int brickId) {
		this.brickId = brickId;
	}

	
	public Brick(int brickId, Color fillColor, int x, int y, int width, int height) {
		super(fillColor, x, y, width, height);
		this.brickId=brickId;
	}
	

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(getFillColor());
		g2.fill(new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight()));
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
