package com.controller;

import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.commands.ClockTickCommand;
import com.commands.CollectedCommand;
import com.commands.Command;
import com.commands.MoveCommand;
import com.components.Clock;
import com.infrastructure.AbstractComponent;
import com.infrastructure.ComponentType;
import com.infrastructure.ObjectListType;
import com.infrastructure.Observer;
import com.observable.GameTimer;
import com.view.WindowFrame;

import com.infrastructure.Constants;

public class GamePlayController implements Observer, KeyListener, ActionListener {
	
	private ArrayList<AbstractComponent> actionList;
	private AbstractComponent gameCharacter;
	private ArrayList<AbstractComponent> collectibleList;
	private ArrayList<AbstractComponent> compositeList;
	private WindowFrame windowFrame;
	private Deque<Command> commandQueue;
	private Clock clock;
	private int collectiblesCollected = 0;
	private GameTimer gameTimer;
	private boolean gameOver = false;
	
	public GamePlayController(WindowFrame windowFrame, GameTimer gameTimer) {
		this.gameTimer = gameTimer;
		
		this.windowFrame = windowFrame;
		loadComponentList();
		
//		this.windowFrame.getMainPanel().addKeyListener(this);
		
//		this.windowFrame.getMainPanel().requestFocus();
		
	}
	
	public void loadComponentList() {

		actionList = new ArrayList<>();
		collectibleList = new ArrayList<>();
		compositeList = windowFrame.getGamePanel().getComponentList();
//		clock = new Clock();
		commandQueue = new LinkedList<>();
		
		for(AbstractComponent abstractComponent : compositeList) {
			
			ObjectListType objectListType = abstractComponent.getObjectProperties().getObjectListType();	
			if(objectListType == ObjectListType.ACTION) {
				actionList.add(abstractComponent);
			} else if(objectListType.equals(ObjectListType.EVENT)) {
				gameCharacter = abstractComponent;
			} else if(objectListType == ObjectListType.COLLECTIBLE) {
				collectibleList.add(abstractComponent);
			}		
		}
	}

	public void save() {
//		pause();
		try {
			String fileName = windowFrame.showSaveDialog();
			if(!fileName.isEmpty()) {
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			
			windowFrame.save(out);
//			out.writeObject(commandQueue);
			out.close();
			fileOut.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}		System.out.println("keyPressed");

	
	}
	
	public void update() {
//		commandQueue.addFirst(new ClockTickCommand(this.clock));
//		System.out.print(actionList.size() + " Size");
		if (!gameOver) { 

			for(AbstractComponent abstractComponent : actionList) {
				int x = abstractComponent.getVelY();
				int y = abstractComponent.getVelX();
				commandQueue.add(new MoveCommand(abstractComponent, x, y));
			}	
			checkCollisionDetection();
			this.windowFrame.draw(null);
		}
	}
	
	
	public void load() {
//		pause();
//		commandQueue.clear();
		try {
			int brickNum = 0;
			String fileName = windowFrame.showOpenDialog();
			if(!fileName.isEmpty()) {
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			
			windowFrame.load(in);

			windowFrame.getMainPanel().requestFocus();
//			commandQueue.clear();
//			Deque<Command> loadCmdQueue = (Deque<Command>) in.readObject();
//			commandQueue.addAll(loadCmdQueue);
//			initCommands();
			in.close();
			fileIn.close();
//			windowFrame.getMainPanel().requestFocus();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		windowFrame.draw(null);
	}

	private void checkCollisionDetection() {
		if (!gameOver) { 
	//		Collision with action objects
			System.out.println("Size of collectible list = " + collectibleList.size());
			System.out.println("Size of actionList list = " + actionList.size());
			outer: for (AbstractComponent actionComponent: actionList)
			{
	//			for (AbstractComponent actionComponent2: actionList)
	//			{
	//				// TODO IF THEY ARESAME COMPONENT
	////				Collision with other action component
	//				if(actionComponent.getBounds().intersects(actionComponent2.getBounds()))
	//				{
	//					actionComponent.setVelX(-actionComponent.getVelX());
	//					actionComponent.setVelY(-actionComponent.getVelY());
	//					actionComponent2.setVelX(-actionComponent2.getVelX());
	//					actionComponent2.setVelY(-actionComponent2.getVelY());
	//				}
	//			}
	//			System.out.println("Action component can collect = " + actionComponent.getCanCollect());
				System.out.println("collectiblesCollected = " + collectiblesCollected);
	
				if(actionComponent.getCanCollect()) {
					for (AbstractComponent collectibleComponent: collectibleList)
					{
		//				Collision with collectible component
						if(actionComponent.getBounds().intersects(collectibleComponent.getBounds()) && collectibleComponent.getVisibility())
						{	
	//						collectibleComponent.performAction();
							commandQueue.addLast(new CollectedCommand(collectibleComponent));
							if(++collectiblesCollected == collectibleList.size())
							{
								gameOver();
								gameOver = true;
								break outer;
							}
							actionComponent.setVelX(-actionComponent.getVelX());
							actionComponent.setVelY(-actionComponent.getVelY());
						}
					}
				}
	//			Collision with game character
	//			System.out.println("actionComponent: " + actionComponent + " gameCharacter: " + gameCharacter);
				if(actionComponent.getBounds().intersects(gameCharacter.getBounds())) {
					actionComponent.setVelX(-actionComponent.getVelX());
					actionComponent.setVelY(-actionComponent.getVelY());
				}
				
	//			Collision action component with right wall
				if(actionComponent.getRightCoordinates() >= Constants.GAME_PANEL_WIDTH)
				{
					actionComponent.setVelX(-actionComponent.getVelX());
				}
				
	//			Collision action component with left wall
				if(actionComponent.getRightCoordinates() - actionComponent.getWidth() <= 0)
				{
					actionComponent.setVelX(-actionComponent.getVelX());
				}
				
	//			Collision action component with up wall
				
				if(actionComponent.getBottomCoordinates() - actionComponent.getHeight() <= 0)
				{
					actionComponent.setVelY(-actionComponent.getVelY());
				}
				
	//			Collision action component with bottom wall
				
				if(actionComponent.getBottomCoordinates() >= Constants.GAME_PANEL_HEIGHT)
				{
					actionComponent.setVelY(-actionComponent.getVelY());
				}
			}
		}
		
	}

	private void gameOver() {
//		this.windowFrame.getGamePanel().
		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Game Over"));
		int result1 = JOptionPane.showConfirmDialog(null, myPanel, 
           "Close", JOptionPane.OK_CANCEL_OPTION);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int curX = Math.abs(this.gameCharacter.getVelX());
		int curY = Math.abs(this.gameCharacter.getVelY());
		System.out.println("keyPressed");
		if (e.getKeyCode() == KeyEvent.VK_LEFT) // && canMoveLeft(this, Constants.getPaddleLeftOffset()
			commandQueue.addFirst(new MoveCommand(this.gameCharacter, -curX, 0));
		
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) //  && canMoveRight(this, Constants.getPaddleRightOffset())
			commandQueue.addFirst(new MoveCommand(this.gameCharacter, curX, 0));

		else if (e.getKeyCode() == KeyEvent.VK_UP) // && canMoveLeft(this, Constants.getPaddleLeftOffset()
			commandQueue.addFirst(new MoveCommand(this.gameCharacter, 0, curY));
		
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) //  && canMoveRight(this, Constants.getPaddleRightOffset())
			commandQueue.addFirst(new MoveCommand(this.gameCharacter, 0, -curY));
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		String commandText = e.getActionCommand();
		if(commandText.equals("Play")) {
			gameTimer.registerObserver(this);
			windowFrame.getMainPanel().requestFocus();
		}
		else if(commandText.equals("Save")) {
			save();
			windowFrame.getMainPanel().requestFocus();
		}
		else if(commandText.equals("Load")) {
			load();
			loadComponentList();
			windowFrame.getMainPanel().requestFocus();
		}
	}
}

