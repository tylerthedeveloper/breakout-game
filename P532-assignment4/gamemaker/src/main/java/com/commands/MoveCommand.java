package com.commands;

import com.infrastructure.AbstractComponent;

public class MoveCommand implements Command {

	protected int oldPositionX; 
	protected int oldPositionY;
	private int oldPositionVelX;
	private int oldPositionVelY;
	private AbstractComponent abstractComponent;
	
	public int getxOffset() {
		return xOffset;
	}

	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	private int xOffset;
	private int yOffset;
	
	public AbstractComponent getComposite() {
		return abstractComponent;
	}

	public void setComposite(AbstractComponent abstractComponent) {
		this.abstractComponent = abstractComponent;
	}

	public int getOldPositionX() {
		return oldPositionX;
	}

	public void setOldPositionX(int oldPositionX) {
		this.oldPositionX = oldPositionX;
	}

	public int getOldPositionY() {
		return oldPositionY;
	}

	public void setOldPositionY(int oldPositionY) {
		this.oldPositionY = oldPositionY;
	}
	
	public int getOldPositionVelX() {
		return oldPositionVelX;
	}

	public void setOldPositionVelX(int oldPositionVelX) {
		this.oldPositionVelX = oldPositionVelX;
	}

	public int getOldPositionVelY() {
		return oldPositionVelY;
	}

	public void setOldPositionVelY(int oldPositionVelY) {
		this.oldPositionVelY = oldPositionVelY;
	}
	
	/**
	 * Store the current state then update
	 */
	public MoveCommand(AbstractComponent abstractComponent, int xOffset, int yOffset) {
		super();
		this.setComposite(abstractComponent);
		this.setOldPositionX(abstractComponent.getX());
		this.setOldPositionY(abstractComponent.getY());
		this.setOldPositionVelX(abstractComponent.getX());
		this.setOldPositionVelY(abstractComponent.getY());
		this.setxOffset(xOffset);
		this.setyOffset(yOffset);
		execute();
	}

	/**
	 * This command was created when the timer ticked, so we advanced the ball accordingly
	 */
	public void execute() {
//		System.out.print("execute");
		if (getxOffset() != 0 && getyOffset() != 0) {
			this.abstractComponent.performAction();
//			System.out.println("  ball");

		}
		else if (getxOffset() != 0) {
			this.abstractComponent.setVelY(0);
			this.abstractComponent.setVelX(xOffset);
			this.abstractComponent.performAction();
			this.abstractComponent.setVelY(this.abstractComponent.getVelY());
		}
		else {
			this.abstractComponent.setVelX(0);
			this.abstractComponent.setVelY(yOffset);
			this.abstractComponent.performAction();
			this.abstractComponent.setVelX(this.abstractComponent.getVelX());
		}
	}

	/**
	 * This command was created when the timer un-ticked, so we return ball to previous state
	 */
	@Override
	public void undo() {
//		this.abstractComponent.setPosition(oldPositionX, oldPositionY);
	}
	
//	public String save() {
//		return "Ball oldPositionX= " + oldPositionX +" oldPositionY= "+oldPositionY;
//	}
	
}
