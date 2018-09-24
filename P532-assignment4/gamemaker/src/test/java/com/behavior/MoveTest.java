package com.behavior;

import org.junit.Test;

import com.infrastructure.ObjectProperties;

import junit.framework.Assert;

public class MoveTest {
	
	Move move;
	ObjectProperties objectProperties;
	public MoveTest()
	{
		objectProperties=new ObjectProperties();
		objectProperties.setCanCollect(true);
		objectProperties.setHeight(10);
		objectProperties.setVelX(10);
		objectProperties.setVelY(10);
		objectProperties.setWidth(20);
		objectProperties.setX(10);
		objectProperties.setY(20);
		move=new Move(objectProperties);
	}

	
	@Test
	public void performActionTest()
	{
		move.performAction();
		Assert.assertEquals(10, objectProperties.getHeight());
	}
}
