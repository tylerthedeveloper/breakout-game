package breakout.ScreenElements;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import breakout.Constants;

public class Clock extends ScreenElement implements Observer{


	private int minutes;
	private int seconds;
	private int count;


	public Clock(Color fillColor, int x, int y, int width, int height) {
		super(fillColor, x, y, width, height);

			count = 1000/Constants.getTimerCount(); //number of frames that should be skipped, to update a second.

		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		//		
		// TODO center box around the time 
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString((minutes+" : "+ seconds),getX() + 5, getY() +50);
	}


	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(count>0) {
			count--;
		}
		else {
			secondPerformed();
			count = 1000/Constants.getTimerCount();
		}
	}

	public void secondPerformed() {
		// TODO Auto-generated method stub
		if(seconds == 59) {seconds =0; minutes++;}
		else if(seconds <60) seconds++;
	}


}