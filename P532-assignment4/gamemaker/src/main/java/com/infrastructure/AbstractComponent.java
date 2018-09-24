package com.infrastructure;

import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class AbstractComponent implements IComposite, Serializable, ActionBehavior {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ActionBehavior actionBehavior;
	private ObjectProperties objectProperties;
	
	public ObjectProperties getObjectProperties() {
		return objectProperties;
	}

	public void setObjectProperties(ObjectProperties objectProperties) {
		this.objectProperties = objectProperties;
	}

	public void setActionBehavior(ActionBehavior actionBehavior) {
		this.actionBehavior = actionBehavior;
	}
	
	public void performAction() {
		actionBehavior.performAction();
	}
	
	
	public AbstractComponent(ObjectProperties objectProperties)
	{
		this.objectProperties = objectProperties;
	}

	
	public int getX() {
		return this.objectProperties.getX();
	}


	public void setX(int x) {
		this.objectProperties.setX(x);
	}
	
	public int getY() {
		return this.objectProperties.getY();
	}


	public void setY(int y) {
		this.objectProperties.setY(y);
	}


	public int getVelX() {
		return this.objectProperties.getVelX();
	}

	public void setVelX(int velX) {
		this.objectProperties.setVelX(velX);
	}


	public int getVelY() {
		return this.objectProperties.getVelY();
	}


	public void setVelY(int velY) {
		this.objectProperties.setVelY(velY);
	}

	public int getWidth() {
		return this.objectProperties.getWidth();
	}


	public void setWidth(int width) {
		this.objectProperties.setWidth(width);
	}


	public int getHeight() {
		return this.objectProperties.getHeight();
	}


	public boolean getCanCollect() {
		return this.objectProperties.getCanCollect();
	}
	
	public void setcanCollect(boolean canCollect) {
		this.objectProperties.setCanCollect(canCollect);
	}
	
	public void setHeight(int height) {
		this.objectProperties.setHeight(height);
	}
	
	public boolean getVisibility()
	{
		return this.objectProperties.isVisibility();
	}
	
	public void setVisbility(boolean visibility)
	{
		this.objectProperties.setVisibility(visibility);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}	
	
	public int getBottomCoordinates() {
		return getY() + getHeight();
	}

	public int getRightCoordinates() {
		return getX() + getWidth();
	}

	@Override
	public void save(ObjectOutputStream op) {
		try {
			op.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void load(ObjectInputStream ip) {
		// TODO Auto-generated method stub
		
	}
	

}
