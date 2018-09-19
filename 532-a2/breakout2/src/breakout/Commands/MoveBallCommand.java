package breakout.Commands;

import breakout.ScreenElements.Ball;

public class MoveBallCommand implements Command {

	private Ball ball;
	protected int oldPositionX; 
	protected int oldPositionY;
	
	/**
	 * Store the current state then update
	 */
	public MoveBallCommand(Ball ball) {
		super();
		this.ball = ball;
		oldPositionX=ball.getX();
		oldPositionY=ball.getY();
		execute();
	}

	/**
	 * This command was created when the timer ticked, so we advanced the ball accordingly
	 */
	public void execute() {
		this.ball.setPosition(oldPositionX,oldPositionY);
		this.ball.updateLocation();
	}

	/**
	 * This command was created when the timer un-ticked, so we return ball to previous state
	 */
	@Override
	public void undo() {
		this.ball.setPosition(oldPositionX,oldPositionY);
	}
	
	public String toString() {
		return "Ball oldPositionX=" + oldPositionX +" oldPositionY= "+oldPositionX;
	}

	
}
