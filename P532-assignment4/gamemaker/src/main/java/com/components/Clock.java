package com.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;

import com.infrastructure.AbstractComponent;
import com.infrastructure.ObjectProperties;

public class Clock extends AbstractComponent implements Serializable {

	private static final long serialVersionUID = 8L;
	
	public long milisecondsElapsed;
//	public final int DELTA;

	public Clock(ObjectProperties objectProperties) {
		super(objectProperties);
//		this.DELTA = delta;
		milisecondsElapsed = 0;
	}

	public void tick(int milisPassed) {
		milisecondsElapsed += milisPassed;
	}

	public String getTime() {
		if (getSeconds() >= 10) {
			return Integer.toString(getMinutes()) + ":" + Integer.toString(getSeconds());
		} else {
			return Integer.toString(getMinutes()) + ":0" + Integer.toString(getSeconds());
		}
	}

	public void reset() {
		milisecondsElapsed = 0;
	}
	
	public void setTime(long ms) {
		this.milisecondsElapsed = ms;
	}

	public int getMinutes() {
		return (int) (milisecondsElapsed / 60000);
	}

	public int getSeconds() {
		return (int) ((milisecondsElapsed / 1000) % 60);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		String min=Integer.toString(getMinutes());
		String sec=Integer.toString(getSeconds());
//		g.drawString(iterator, x, y);
		g.setColor(Color.RED);
		g.setFont(new Font("Helvetica", Font.BOLD, 30));
		g.drawString(min+":"+sec, 200 , 80);
		
	}
}
