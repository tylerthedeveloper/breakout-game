package breakout.ScreenElements;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import breakout.Constants;

public class ScoreBoard extends ScreenElement implements Sprite {
	int score=0;
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public ScoreBoard(Color fillColor, int x, int y, int width, int height) {
		super(fillColor, x, y, width, height);
		score=0;
	}

	@Override
	public void draw(Graphics g) {
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString(String.valueOf(score),getX() + 5, getY() +50);
	}

	public void incrementScore() {
		++score;
	}
	
	public void decrementScore() {
		--score;
	}
	
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		this.score = 0;
	}
	
}
