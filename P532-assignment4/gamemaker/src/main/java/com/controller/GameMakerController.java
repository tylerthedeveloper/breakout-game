package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.behavior.Move;
import com.behavior.Visibility;
import com.components.Ball;
import com.components.Brick;
import com.components.Fire;
import com.components.Paddle;
import com.infrastructure.AbstractComponent;
import com.infrastructure.ComponentType;
import com.infrastructure.ObjectListType;
import com.infrastructure.ObjectProperties;
import com.view.WindowFrame;

public class GameMakerController implements ActionListener, MouseListener {
	
	private WindowFrame windowFrame;

	public GameMakerController(WindowFrame windowFrame)
	{	
		this.windowFrame = windowFrame;
	}

	public void displayButtons() {
		this.windowFrame.getFormPanel().createButtons();
	}

	public void actionPerformed(ActionEvent e) {}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		ObjectProperties selected = new ObjectProperties();
		ObjectProperties formPanelSelected = windowFrame.getFormPanel().getSelected();
		
		
		if (formPanelSelected != null) {
			
            int x = arg0.getX(); 
            int y = arg0.getY(); 
            selected.setX(x);
            selected.setY(y);

            ComponentType componentType = formPanelSelected.getComponentType();
        	AbstractComponent abstractComponent = null;
        	selected.setComponentType(formPanelSelected.getComponentType());
        	selected.setObjectListType(formPanelSelected.getObjectListType());
//        	System.out.println("Game maker controller can collect = " + formPanelSelected.getObjectListType());
        	selected.setHeight(formPanelSelected.getHeight());
        	selected.setWidth(formPanelSelected.getWidth());
        	selected.setVelX(formPanelSelected.getVelX());
        	selected.setVelY(formPanelSelected.getVelY());
			selected.setCanCollect(formPanelSelected.getCanCollect());

        	switch(componentType) {
        		case BALL:
        		{
        			abstractComponent = new Ball(selected);
        			break;	
        		}
        		case BRICK:
        		{
//        			System.out.println( selected.getObjectListType() +  " Brick set as collectible");
        			abstractComponent = new Brick(selected);
        			break;
        		}
        		case PADDLE:
        		{
        			abstractComponent = new Paddle(selected);
        			break;
        		}
        		case FIRE:
        		{
        			abstractComponent = new Fire(selected);
        			break;
        		}      
        		case BACKGROUND:
        		{
        			windowFrame.getFormPanel().createSetBackgroundButton();
        		}
        	}
        	
        	if(abstractComponent.getObjectProperties().getObjectListType() == ObjectListType.COLLECTIBLE) {
        		// set behavior to the object to visibility
        		System.out.println("visisbility");
        		Visibility visibility = new Visibility(selected);
        		abstractComponent.setActionBehavior(visibility);
        	}
        	
        	if(abstractComponent.getObjectProperties().getObjectListType() == ObjectListType.EVENT ||
        			 abstractComponent.getObjectProperties().getObjectListType() == ObjectListType.ACTION) {
        		// set behavior to move
        		Move move = new Move(selected);
        		abstractComponent.setActionBehavior(move);
        	}

        	System.out.println(" comp typ  " + abstractComponent.getObjectProperties().getObjectListType());
        	windowFrame.getGamePanel().addComponent(abstractComponent);
        	windowFrame.draw(null);
		}	
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
