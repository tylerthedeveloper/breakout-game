package breakout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import breakout.Commands.BrickCommand;
import breakout.Commands.ClockTickCommand;
import breakout.Commands.Command;
import breakout.Commands.MoveBallCommand;
import breakout.Commands.MovePaddleCommand;
import breakout.Commands.ScoreCommand;
import breakout.ScreenElements.Ball;
import breakout.ScreenElements.Board;
import breakout.ScreenElements.Brick;
import breakout.ScreenElements.Clock;
import breakout.ScreenElements.Sprite;
import breakout.ScreenElements.Paddle;
import breakout.ScreenElements.ScoreBoard;
import breakout.ScreenElements.ScreenElement;
import audio.Sounds;

public class ScreenManager extends JPanel implements KeyListener, Sprite {

	private static final int PANEL_WIDTH = Constants.getPANEL_WIDTH();
	private static final int PANEL_HEIGHT = Constants.getPANEL_HEIGHT();
	
	private ArrayList<ScreenElement> ScreenElements = new ArrayList<>();
	public static ArrayList<Brick> brickList;
	
	private Clock clock;  
	private Paddle paddle;
	private Ball ball;
	private Board board;
	private Time time;
	private Window window;
	private ScoreBoard scoreBoard;
//	private Sounds Sound;
	private int brickCount = 3;
	private Deque<Command> queue;
	
	public ScreenManager() 
	{
		//initializing class variables 
		super.setBackground(Color.WHITE);
		this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		brickList = new ArrayList<>();
		addKeyListener(this);
		requestFocus();
		queue = new ArrayDeque<>();

		ScreenElements.add(new Clock(Color.BLACK, Constants.getClockPosX(), Constants.getClockPosY(), Constants.getClockWidth(), Constants.getClockHeight())); 
		ScreenElements.add(new Paddle(Color.GRAY, Constants.getPaddlePosX(), Constants.getPaddlePosY(), Constants.getPaddleWidth(), Constants.getPaddleHeight()));		
		ScreenElements.add(new Ball(Color.RED, Constants.getBallPosX(), Constants.getBallPosY(), Constants.getBallRadius()));
		ScreenElements.add(new Board(Color.BLACK, Constants.getBoardPosX(), Constants.getBoardPosY(), Constants.getBoardWidth(), Constants.getBoardHeight()));
		ScreenElements.add(new ScoreBoard(Color.BLACK, Constants.getScoreBoardPosX(), Constants.getScoreBoardPosY(), Constants.getScoreBoardWidth(), Constants.getScoreBoardHeight()));
		

		init();
		
		// Track class variables
		for (ScreenElement element : ScreenElements) {
			if (element instanceof Clock)
				this.clock = (Clock)element;
			else if (element instanceof Paddle)
				this.paddle = (Paddle)element;
			else if (element instanceof Ball)
				this.ball = (Ball)element;
			else if (element instanceof Board)
				this.board = (Board)element;
			else if (element instanceof ScoreBoard)
				this.scoreBoard = (ScoreBoard)element;
				
			this.add(element);
		}
		
		
		// Initialize observable and register observer
		time = new Time();
		time.registerObserver(this);
	}
	
	/**
	 * Used for state initialization
	 */
	public void init() {
		brickList = new ArrayList<>();
		int brickPos = Constants.getBoardPosX();
		for(int i = 0; i < brickCount; i++) {
			Brick b;
			if(i % 2 == 0) {
				b = new Brick(i,Color.BLUE, brickPos, Constants.getBrickPosY(), Constants.getBrickWidth(), Constants.getBrickHeight());				
			} else {
				b = new Brick(i,Color.GREEN, brickPos, Constants.getBrickPosY(), Constants.getBrickWidth(), Constants.getBrickHeight());				
			}
			brickList.add(b);
			brickPos += Constants.getBrickWidth();
		}
		
		for (ScreenElement element : this.ScreenElements) {
			element.reset();
		}
	}
	
	/**
     * Abstract implementation
	 */
	public void paint(Graphics g) {
	    super.paintComponent(g);
	    for (ScreenElement element : ScreenElements) {
    		element.draw(g);
	    }
	    for(ScreenElement element : brickList) {
    		element.draw(g);
	    }
    }
     
    /** 
     * Abstract implementation
     */
    public void draw(Graphics g) {
		paint(g);
    }
    
    /**
     * Helper used for replay
     */
    void emptyQueue() {
		queue.clear();
    }
    
    /**
     * Keylistener for moving the paddle
     */
	@Override public void keyPressed(KeyEvent e) {
		if (time.isRunning()) {
			queue.addFirst(new MovePaddleCommand(this.paddle));
			paddle.collisionResponse(e);			
		}
	}

	/**
	 * Our observer implementation 
	 */
	@Override
	public void update() {
		queue.addFirst(new MoveBallCommand(this.ball));
		queue.addFirst(new ClockTickCommand(this.clock));
	
		collisionDetection();
		revalidate();
		repaint();
	}

	/**
	 * Check to see if the ball has collided with any object
	 */
	public void collisionDetection() {
	
		int prevOffsetX = ball.getSpeedX();
		int prevOffsetY = ball.getSpeedY();
		
		if(brickList.size() == 0) {
			this.gameOver();
		}
		
		isBallCollidingWithBoard();
		
		if(isElementCollidingWithBall(paddle)) {
			Sounds.BALL_PADDLE.play();
			reflectBall(paddle, prevOffsetX, prevOffsetY);
		}
		
		Brick collisionBrick = getCollisionBrick();
		if(collisionBrick != null) {
			Sounds.BALL_BRICK.play();
			reflectBall(collisionBrick, prevOffsetX, prevOffsetY);
			removeBrick(collisionBrick);
		}
		
		ball.updateLocation(ball.getSpeedX(), ball.getSpeedY());

	}

	/**
	 * Check if a brick has been hit
	 */
	private Brick getCollisionBrick() {
		for(int i=0;i<brickList.size();i++) {
			Brick b = brickList.get(i);
			if (isElementCollidingWithBall(b)) {
				queue.addFirst(new BrickCommand(b));
				return b;
			}
		}
		return null;
	}
	
	/**
	 * A brick with id brickId has been hit and will be removed
	 */
	protected void removeBrick(Brick brickId) {
		remove(brickId);
		revalidate();
		queue.addFirst(new ScoreCommand(scoreBoard));
	}

	/**
	 * The ball has hit an object and will change it's course
	 */
	private void reflectBall(ScreenElement element, int prevOffsetX, int prevOffsetY) {
				
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
	
	private void isBallCollidingWithBoard() {
		
		// TODO: put into screen element 
		
		// CAN MOVE LEFT
		
		// CAN MOVE RIGHT
		
		// CAN MOVE UP
		
		// CAN MOVE DOWN
		
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
	
	/**
	 * The game has ended because all bricks ahve been hit
	 */
	public void gameOver() {
		this.removeKeyListener(this);
		Object[] options = {"Reset", "Exit"}; 
		Sounds.GAME_WON.play();
		int a = JOptionPane.showOptionDialog(
			getParent(), "Your score is :" + scoreBoard.getScore(), 
			"Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
			null, options, null
		);
		this.time.stopTimer();
		if(a == JOptionPane.YES_OPTION) {
			this.resetGame();
		}
		else {
			System.exit(0);
		}		
	}
	
	/**
	 * Return the game to starting state
	 */
	public void resetGame() {
		init();
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
		frame.dispose();
		this.removeAll();
		revalidate();
		Window.startGame();
	}
	
	
	/**
	 * Pause the game play
	 */
	public void pauseGame() {
		if(time.isRunning()) {
			this.time.stopTimer();	
		} else {
			this.time.startTimer();
		}
		requestFocus();
	}
	
	/**
	 * Continue the game play
	 */
	public void resumeGame() {
		this.time.startTimer();
		requestFocus();
	}
	
	/**
	 * Undo button: returns the state of the application to the previous command that was executed
	 */
	// TODO: pause or disable undo
	public void undo() {
		if (!time.isRunning()) {		
			int k =10;
			while(!queue.isEmpty() && k > 0) {
			Command c = queue.removeFirst();
			c.undo();
			k--;
			}
			repaint();
		}
		requestFocus();
	}
	
	
	/**
	 * Replay button: re-runs all the events that have happened thus far 
	 */
	public void replay()  {
		if (!time.isRunning()) {		
			System.out.println("Replay called");
			init();
			Iterator<Command> it= queue.descendingIterator();
			   Timer timer = new Timer(10, new ActionListener() {
			        @Override
			        public void actionPerformed(ActionEvent e) {
//			            stop=!it.hasNext();
			        		if(it.hasNext()) {
							Command c = (Command) it.next();
							revalidate();
							repaint();
							c.execute();
			            } else {
			                ((Timer)e.getSource()).stop();
			            }
			        }
			    });
			    timer.setRepeats(true);
			    timer.setDelay(4);
			    timer.start();
		}
		requestFocus();
	}
	
	// Abstract methods that need to be implemented
	@Override public void keyTyped(KeyEvent e) {}
	@Override public void keyReleased(KeyEvent e) {}

}
