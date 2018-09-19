package breakout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import breakout.ScreenElements.Ball;
import breakout.ScreenElements.Board;
import breakout.ScreenElements.Brick;
import breakout.ScreenElements.Clock;
import breakout.ScreenElements.Observer;
import breakout.ScreenElements.Paddle;
import breakout.ScreenElements.ScoreBoard;
import breakout.ScreenElements.ScreenElement;
import breakout.sounds.Sounds;

public class ScreenManager extends JPanel implements KeyListener, Observer{

	private static final int PANEL_WIDTH = Constants.getPANEL_WIDTH();
	private static final int PANEL_HEIGHT = Constants.getPANEL_HEIGHT();
	
	private ArrayList<ScreenElement> ScreenElements = new ArrayList<>();
	private ArrayList<Brick> brickList;
	
	private Clock clock;  
	private Paddle paddle;
	private Ball ball;
	private Board board;
	private Brick brick;
	private Time time;
	private int brickCount = 1;
	
	public ScreenManager() 
	{
		//initializing class variables 
		super.setBackground(Color.WHITE);
		this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		brickList = new ArrayList<>();

		ScreenElements.add(new Clock(Color.BLACK, Constants.getClockPosX(), Constants.getClockPosY(), Constants.getClockWidth(), Constants.getClockHeight())); 
		ScreenElements.add(new Paddle(Color.GRAY, Constants.getPaddlePosX(), Constants.getPaddlePosY(), Constants.getPaddleWidth(), Constants.getPaddleHeight()));		
		ScreenElements.add(new Ball(Color.RED, Constants.getBallPosX(), Constants.getBallPosY(), Constants.getBallRadius()));
		ScreenElements.add(new Board(Color.BLACK, Constants.getBoardPosX(), Constants.getBoardPosY(), Constants.getBoardWidth(), Constants.getBoardHeight()));
		ScreenElements.add(new ScoreBoard(Color.BLACK, Constants.getScoreBoardPosX(), Constants.getScoreBoardPosY(), Constants.getScoreBoardWidth(), Constants.getScoreBoardHeight()));
		int brickPos = Constants.getBoardPosX();
		for(int i = 0; i < brickCount; i++) {
			Brick b;
			if(i % 2 == 0)
				b = new Brick(Color.BLUE, brickPos, Constants.getBrickPosY(), Constants.getBrickWidth(), Constants.getBrickHeight());
			else
				b = new Brick(Color.GREEN, brickPos, Constants.getBrickPosY(), Constants.getBrickWidth(), Constants.getBrickHeight());
			brickList.add(b);
			brickPos += Constants.getBrickWidth();
		}
		
		for (ScreenElement element : ScreenElements) {
			if (element instanceof Clock)
				this.clock = (Clock)element;
			if (element instanceof Paddle)
				this.paddle = (Paddle)element;
			if (element instanceof Ball)
				this.ball = (Ball)element;
			if (element instanceof Board)
				this.board = (Board)element;
				
			this.add(element);
		}

		// Initialize observable and register observer
		time = new Time();
		time.registerObserver(clock);
		time.registerObserver(ball);
		time.registerObserver(this);
	}
	
     public void paint(Graphics g) {
        super.paintComponent(g);
        for (ScreenElement element : ScreenElements) {
        	element.draw(g);
        }
        for(ScreenElement element : brickList) {
        	element.draw(g);
        }
    }

	@Override public void keyPressed(KeyEvent e) {
		paddle.collisionResponse(e);
	}

	@Override public void keyTyped(KeyEvent e) {}
	@Override public void keyReleased(KeyEvent e) {}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		collisionDetection();
		revalidate();
		repaint();
	}

	public void collisionDetection() {
	
		int prevOffsetX = ball.getSpeedX();
		int prevOffsetY = ball.getSpeedY();
		
		if(brickCount == 0)
			this.gameOver();
		
		checkCollisionWithBoard();
		if(isCollision(paddle)) {
			Sounds.BALL_PADDLE.play();
			checkCollisionWith(paddle, prevOffsetX, prevOffsetY);
			
		}
		
		Brick collisionBrick = getCollisionBrick();
		if(collisionBrick != null) {
			Sounds.BALL_BRICK.play();
			checkCollisionWith(collisionBrick, prevOffsetX, prevOffsetY);
			removeBrick(collisionBrick);
		}
		
		ball.moveBall(ball.getSpeedX(), ball.getSpeedY());

		
	}
	
	private Brick getCollisionBrick() {
		Brick b = null;
		int i = 0;
		for(Brick brick : brickList) {
			if (isCollision(brick)) {
				b = brick;
				brickList.remove(i);
				return b;
			}
			i++;
		}
		return null;
	}
	
	private void removeBrick(Brick brickId) {
		this.remove(brickId);
		this.revalidate();
		brickCount--;
		Constants.setScore(Constants.getScore()+1);
	}

	private void checkCollisionWith(ScreenElement element, int prevOffsetX, int prevOffsetY) {
				
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
	
	private void checkCollisionWithBoard() {
		
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
	
	
	
	
	public void gameOver() {
		this.removeKeyListener(this);
		Object[] options = {"Reset", "Exit"}; 
		int a = JOptionPane.showOptionDialog(getParent(), "Your score is :" + Constants.getScore(), "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE
				, null, options, null);
		this.time.stopTimer();
		if(a == JOptionPane.YES_OPTION) {
			Constants.setScore(0);
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
			frame.dispose();
			this.removeAll();
			revalidate();
			Window.startGame();
		}
		else
			System.exit(0);
		
	}
	
	
	public boolean isCollision(ScreenElement screenElement) {
		Area elem1;
		Area elem2;

		elem1 = new Area(screenElement.getBounds2D());
		
		elem2 = new Area(ball.getBounds2DBall());
		
		elem2.intersect(elem1);
		return !elem2.isEmpty();
	}
}
