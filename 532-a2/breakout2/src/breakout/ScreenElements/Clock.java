package breakout.ScreenElements;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import breakout.Constants;

public class Clock extends ScreenElement implements Sprite{


	private int minutes;
	private int seconds;
	private int count;


	public Clock(Color fillColor, int x, int y, int width, int height) {
		super(fillColor, x, y, width, height);

	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public void draw(Graphics g) {
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString(((minutes<10?"0":"") + minutes+" : "+ (seconds<10?"0":"") + seconds),getX() + 5, getY() +50);
	}

	@Override
	public void update() {
	}
	public void execute() {
		seconds=(int) Math.floor((++count)*Constants.getTimerCount()/1000);
		minutes=seconds/60;
		seconds=seconds%60;
	}
	public void undo() {
		
	}

	public void timeIncrement() {
		if(seconds == 59) {seconds =0; minutes++;}
		else if(seconds <60) seconds++;
	}

	public void timeDecrement() {
		if(minutes>0) {
			if(seconds == 0) 
			{
				seconds =59; minutes--;
			}
		}
		else if(seconds >0) 
			seconds--;
	}

	@Override
	public void reset() {
		this.count = 0;
	}

}