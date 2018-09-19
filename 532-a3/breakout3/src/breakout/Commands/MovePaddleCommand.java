package breakout.Commands;

import breakout.ScreenElements.Paddle;

public class MovePaddleCommand implements Command {

	private Paddle paddle;
	protected int oldPositionX; 
	protected int oldPositionY;

	/**
	 * Store the current state then update
	 */
	public MovePaddleCommand(Paddle paddle) {
		super();
		this.paddle = paddle;
		oldPositionX=paddle.getX();
		oldPositionY=paddle.getY();
		execute();
	}

	/**
	 * This command was created when a key was pressed, so we update the location accordingly
	 */
	public void execute() {
		this.paddle.setPosition(oldPositionX,oldPositionY);
	}

	/**
	 * This command was created when a key was un-pressed, so we update the location accordingly
	 */
	@Override
	public void undo() {
		this.paddle.setPosition(oldPositionX,oldPositionY);
	}
	
	public String save() {
		return "Paddle oldPositionX= " + oldPositionX +" oldPositionY= "+oldPositionY;
	}
	public void setOldPositionX(int oldPositionX) {
		this.oldPositionX = oldPositionX;
	}

	public void setOldPositionY(int oldPositionY) {
		this.oldPositionY = oldPositionY;
	}

}
