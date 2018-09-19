package breakout;

import java.awt.geom.Area;

import breakout.ScreenElements.*;

public class Collision {
	private Ball ball;
	private Board board;
	
	public Collision(Ball ball, Board board) {
		this.ball = ball;
		this.board = board;
	}
	
	/**
	 * The ball has hit an object and will change it's course
	 */
	public void reflectBall(ScreenElement element, int prevOffsetX, int prevOffsetY) {
		
		//right down to left up
		if((ball.getX() <= ball.getX() - prevOffsetX && ball.getY() >= ball.getY()-prevOffsetY)) {
			ball.setSpeedX(-Constants.getBallSpeed());
			ball.setSpeedY(-Constants.getBallSpeed());
			//right down to right down - hits right side of element 
			if(ball.getYRadiusCoordinates() > element.getY() && ball.getXRadiusCoordinates() > element.getRightXCoordinates()) {
				ball.setSpeedX(Constants.getBallSpeed());
				ball.setSpeedY(Constants.getBallSpeed());
			}
		}
		//left down to right up
		else if (ball.getX() >= ball.getX() - prevOffsetX && ball.getY() >= ball.getY() - prevOffsetY) {
			ball.setSpeedX(Constants.getBallSpeed());
			ball.setSpeedY(-Constants.getBallSpeed());
			//left down to left down - hits left side of element
			if(ball.getYRadiusCoordinates() > element.getY() && ball.getXRadiusCoordinates() < element.getX() ) { 
				ball.setSpeedX(-Constants.getBallSpeed());
				ball.setSpeedY(Constants.getBallSpeed());
			}
		}
		//left up to right down
		else if(ball.getX() >= ball.getX() - prevOffsetX && ball.getY() <= ball.getY()-prevOffsetY) {
			ball.setSpeedX(Constants.getBallSpeed());
			ball.setSpeedY(Constants.getBallSpeed());
			// left up to left up - hits left side of element
			if(ball.getYRadiusCoordinates() < element.getBottomYCoordinates() && ball.getXRadiusCoordinates() < element.getX()) {
				ball.setSpeedX(-Constants.getBallSpeed());
				ball.setSpeedY(-Constants.getBallSpeed());
			}
		}
		//right up to left down
		else if(ball.getX() <= ball.getX() - prevOffsetX && ball.getY() <= ball.getY()-prevOffsetY) {
			ball.setSpeedX(-Constants.getBallSpeed());
			ball.setSpeedY(Constants.getBallSpeed());
			// right up to right up - hits right side of element
			if(ball.getYRadiusCoordinates() < element.getBottomYCoordinates() && ball.getXRadiusCoordinates() > element.getRightXCoordinates()) {
				ball.setSpeedX(Constants.getBallSpeed());
				ball.setSpeedY(-Constants.getBallSpeed());
			}
		}
		
	}
	
	public void isBallCollidingWithBoard() {
		
		int currentBallPosX = ball.getX();
		int currentBallPosY = ball.getY();
		int leftWallPosition = board.getX();
		int rightWallPosition = board.getX()+ board.getWidth() - ball.getWidth();
		int topWallPosition = board.getY();
		int bottomWallPosition = board.getY()+board.getHeight()- ball.getWidth();
				
		//check for hit on the wall
		boolean hitLeftWall = currentBallPosX+ ball.getSpeedX() < leftWallPosition;
		boolean hitRightWall = currentBallPosX + ball.getSpeedX() > rightWallPosition;
		boolean hitTopWall = currentBallPosY + ball.getSpeedY() < topWallPosition;
		boolean hitBottomWall = currentBallPosY + ball.getSpeedY() > bottomWallPosition;
		
		//if ball hits one of the vertical sides of wall
		if(hitLeftWall || hitRightWall) ball.setSpeedX(-1 * ball.getSpeedX());
		//if ball hits one of the horizontal sides of wall
		if(hitTopWall || hitBottomWall) ball.setSpeedY(-1 * ball.getSpeedY());

	}
	
	
	public boolean isElementCollidingWithBall(ScreenElement screenElement) {
		Area elem1;
		Area elem2;
		elem1 = new Area(screenElement.getBounds2D());
		elem2 = new Area(ball.getBounds2DBall());
		elem2.intersect(elem1);
		return !elem2.isEmpty();
	}
}
