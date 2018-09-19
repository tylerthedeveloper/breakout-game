package breakout.ScreenElements;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import breakout.Constants;


public class ScoreBoard extends ScreenElement implements Observer{

	public ScoreBoard(Color fillColor, int x, int y, int width, int height) {
		super(fillColor, x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString(String.valueOf(Constants.getScore()),getX() + 5, getY() +50);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
