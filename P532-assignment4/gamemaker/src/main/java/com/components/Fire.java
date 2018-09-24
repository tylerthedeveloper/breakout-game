package com.components;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import com.infrastructure.AbstractComponent;
import com.infrastructure.Constants;
import com.infrastructure.IComposite;
import com.infrastructure.ObjectProperties;

public class Fire extends AbstractComponent  {

	private BufferedImage image; 	
	
	public Fire(ObjectProperties objectProperties)
	{
		super(objectProperties);
		try {
			this.image = ImageIO.read(new File(Constants.fireImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
			this.image = image;
	}

	public void draw(Graphics g) {
		g.drawImage(this.getImage(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);		
	}

}
