package breakout.ScreenElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;


public class Board extends ScreenElement {
	
	public Board(Color fillColor, int x, int y, int width, int height) {
		super(fillColor, x, y, width, height);
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D.Double board = new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
		elementBounds = board.getBounds2D();
		g2.setColor(getFillColor());
		g2.draw(board);
	}

	@Override
	public void reset() {
		
	}

}
