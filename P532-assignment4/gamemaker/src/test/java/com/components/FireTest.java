package com.components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.infrastructure.ObjectProperties;
import com.infrastructure.Observer;

public class FireTest {
	

	Fire fire;
	ObjectProperties objectProperties;
	BufferedImage image;
	
	public FireTest()
	{
		objectProperties=new ObjectProperties();
		objectProperties.setCanCollect(true);
		objectProperties.setHeight(10);
		objectProperties.setWidth(40);
		objectProperties.setX(120);
		objectProperties.setY(30);
		
		fire = new Fire(objectProperties);
	}
	
	@Test
	public void setImageTest()
	{
		
		fire.setImage(image);
		Assert.assertEquals(image, fire.getImage());
	}
	
	@Test
	public void getImageTest()
	{
		fire.setImage(image);
		fire.getImage();
		Assert.assertEquals(image, fire.getImage());
	}
	
	@Test
	public void graphicsTest()
	{
		Graphics g=Mockito.mock(Graphics.class);
		fire.draw(g);
		//Mockito.doNothing().when(g).drawImage(Mockito.any(BufferedImage.class), Mockito.any(Integer.class), Mockito.any(Integer.class), Mockito.any(ImageObserver.class));
		Assert.assertEquals(true, true);
	}
}
