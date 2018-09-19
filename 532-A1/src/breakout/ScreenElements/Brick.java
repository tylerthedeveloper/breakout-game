package breakout.ScreenElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Brick extends ScreenElement {
	public Brick(Color fillColor, int x, int y, int width, int height) {
		super(fillColor, x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(getFillColor());
		g2.fill(new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight()));
	}


}
