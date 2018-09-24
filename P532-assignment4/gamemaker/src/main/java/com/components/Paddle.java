package com.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

import com.infrastructure.AbstractComponent;
import com.infrastructure.Constants;
import com.infrastructure.IComposite;
import com.infrastructure.ObjectProperties;

public class Paddle extends AbstractComponent {
	
	public Paddle(ObjectProperties objectProperties) {
		super(objectProperties);
	}

	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
}
