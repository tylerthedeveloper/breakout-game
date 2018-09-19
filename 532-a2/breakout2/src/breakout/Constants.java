package breakout;

public class Constants {

	private static int SCREEN_WIDTH = 1300;
	private static int SCREEN_HEIGHT = 800;	
	private static int PANEL_HEIGHT = 800;
	private static int PANEL_WIDTH = 800;
	
	private static int ballPosX = 350;
	private static int ballPosY = 300;
	private static int ballRadius = 20;
	
	private static int ballSpeedY = 2;
	private static int ballSpeedX = 2;
	private static int ballSpeed = 2;
	
	private static int clockPosX = 10;
	private static int clockPosY = 10;
	private static int clockWidth = 140;
	private static int clockHeight = 60;
	
	private static int scoreBoardPosX = 870;
	private static int scoreBoardPosY = 10;
	private static int scoreBoardWidth = 50;
	private static int scoreBoardHeight = 60;
	
	private static int paddlePosX = 400;
	private static int paddlePosY = 500;
	private static int paddleWidth = 100;
	private static int paddleHeight = 40;
	private static int paddleDisplacement = 5; 
	private static int paddleLeftOffset = -30;
	private static int paddleRightOffset = 30;
	
	private static int boardPosX = 160;
	private static int boardPosY = 10;
	private static int boardWidth = 700;
	private static int boardHeight = 700;
	
	private static int brickPosX = 400;
	private static int brickPosY = 150;
	private static int brickWidth = 50;
	private static int brickHeight = 50;
	
	private static int timerCount = 7;


	public static int getTimerCount() {
		return timerCount;
	}
	public static int getBrickCount() {
		return Constants.getBoardWidth()/Constants.getBrickWidth();
	}
	public static int getSCREEN_WIDTH() {
		return SCREEN_WIDTH;
	}
	public static int getSCREEN_HEIGHT() {
		return SCREEN_HEIGHT;
	}
	public static int getPANEL_HEIGHT() {
		return PANEL_HEIGHT;
	}
	public static int getPANEL_WIDTH() {
		return PANEL_WIDTH;
	}
	public static int getBallPosX() {
		return ballPosX;
	}
	public static int getBallPosY() {
		return ballPosY;
	}
	public static int getBallRadius() {
		return ballRadius;
	}
	public static int getBallSpeedX() {
		return ballSpeedX;
	}
	public static int getBallSpeedY() {
		return ballSpeedY;
	}
	public static int getClockPosX() {
		return clockPosX;
	}
	public static int getClockPosY() {
		return clockPosY;
	}
	public static int getClockWidth() {
		return clockWidth;
	}
	public static int getClockHeight() {
		return clockHeight;
	}
	public static int getPaddlePosY() {
		return paddlePosY;
	}
	public static int getPaddleWidth() {
		return paddleWidth;
	}
	public static int getPaddleHeight() {
		return paddleHeight;
	}
	public static int getPaddlePosX() {
		return paddlePosX;
	}
	public static int getPaddleLeftOffset() {
		return paddleLeftOffset;
	}
	public static int getPaddleRightOffset() {
		return paddleRightOffset;
	}
	public static int getPaddleDisplacement() {
		return paddleDisplacement;
	}
	public static void setPaddleDisplacement(int paddleDisplacement) {
		Constants.paddleDisplacement = paddleDisplacement;
	}
	public static int getBoardPosX() {
		return boardPosX;
	}
	public static int getBoardPosY() {
		return boardPosY;
	}
	public static int getBoardWidth() {
		return boardWidth;
	}
	public static int getBoardHeight() {
		return boardHeight;
	}
	public static int getBrickPosX() {
		return brickPosX;
	}
	public static int getBrickPosY() {
		return brickPosY;
	}
	public static int getBrickWidth() {
		return brickWidth;
	}
	public static int getBrickHeight() {
		return brickHeight;
	}
	public static int getScoreBoardPosX() {
		return scoreBoardPosX;
	}
	public static int getScoreBoardPosY() {
		return scoreBoardPosY;
	}
	public static int getScoreBoardWidth() {
		return scoreBoardWidth;
	}
	public static int getScoreBoardHeight() {
		return scoreBoardHeight;
	}
	public static int getBallSpeed() {
		return ballSpeed;
	}
	
}
