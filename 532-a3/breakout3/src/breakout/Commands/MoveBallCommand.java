package breakout.Commands;

import breakout.ScreenElements.Ball;

public class MoveBallCommand implements Command {

	private Ball ball;
	protected int oldPositionX; 
	protected int oldPositionY;
	
	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
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
	
	public String save() {
		return "Ball oldPositionX= " + oldPositionX +" oldPositionY= "+oldPositionY;
	}

	
}
