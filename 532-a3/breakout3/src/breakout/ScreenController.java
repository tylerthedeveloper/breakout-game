package breakout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.Iterator;
import java.util.Properties;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


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

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class ScreenController extends JPanel implements KeyListener, Sprite {

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
	private Sounds Sound;
	private int brickCount = 3;
	private Deque<Command> queue;
	private Deque<ArrayList<Command>> saveQueue;
	private Collision collision;
	final static Logger logger = Logger.getLogger(ScreenController.class);
	
	public ScreenController() 
	{
		//initializing class variables 
		super.setBackground(Color.WHITE);
		this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		brickList = new ArrayList<>();
		addKeyListener(this);
		requestFocus();
		queue = new ArrayDeque<>();
		saveQueue = new ArrayDeque<>();
	 
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
		
		collision = new Collision(ball, board);
		
		logger.info("Starting Game");
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
			logger.info("Paddle moved");
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
		
		collision.isBallCollidingWithBoard();
		
		if(collision.isElementCollidingWithBall(paddle)) {
			Sounds.BALL_PADDLE.play();
			collision.reflectBall(paddle, prevOffsetX, prevOffsetY);
		}
		
		Brick collisionBrick = getCollisionBrick();
		if(collisionBrick != null) {
			Sounds.BALL_BRICK.play();
			collision.reflectBall(collisionBrick, prevOffsetX, prevOffsetY);
			removeBrick(collisionBrick);
		}
		
		ball.updateLocation(ball.getSpeedX(), ball.getSpeedY());

	}

	/**
	 * Check if a brick has been hit
	 */
	public Brick getCollisionBrick() {
		for(int i=0;i<brickList.size();i++) {
			Brick b = brickList.get(i);
			if (collision.isElementCollidingWithBall(b)) {
				queue.addFirst(new BrickCommand(b));
				return b;
			}
		}
		return null;
	}
	
	public Collision getCollision() {
		return collision;
	}

	public void setCollision(Collision collision) {
		this.collision = collision;
	}

	public static ArrayList<Brick> getBrickList() {
		return brickList;
	}

	public static void setBrickList(ArrayList<Brick> brickList) {
		ScreenController.brickList = brickList;
	}

	/**
	 * A brick with id brickId has been hit and will be removed
	 */
	public void removeBrick(Brick brickId) {
		logger.info("brick exploded");
		remove(brickId);
		revalidate();
		queue.addFirst(new ScoreCommand(scoreBoard));
	}
	
	/**
	 * The game has ended because all bricks have been hit
	 */
	public void gameOver() {
		logger.info("Game Over");
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
			logger.info("Game Exited");
		}		
	}
	
	/**
	 * Return the game to starting state
	 */
	public void resetGame() {
		logger.info("Restarted Game");
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
		logger.info("Pausing Game");
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
		logger.info("Resuming Game");
		this.time.startTimer();
		requestFocus();
	}
	
	/**
	 * Undo button: returns the state of the application to the previous command that was executed
	 */
	// TODO: pause or disable undo
	public void undo() {
		logger.info("Undo called");
		if (!time.isRunning()) {		
			Command c = queue.removeFirst();
			c.undo();
			repaint();
		}
		requestFocus();
	}
	
	/**
	 * Replay button: re-runs all the events that have happened thus far 
	 */
	public void replay()  {
		logger.info("Replaying the game");
		if (!time.isRunning()) {		
			init();
			Iterator<Command> it= queue.descendingIterator();
			   Timer timer = new Timer(10, new ActionListener() {
			        @Override
			        public void actionPerformed(ActionEvent e) {
			        		if(it.hasNext()) {
							Command c = it.next();
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
	
	/**
	 * Save last game action
	 */
	public void saveGame() {
		logger.info("saved game state");
		Iterator<Command> itr = queue.descendingIterator();
		ArrayList<Command> saveCommands = new ArrayList<>();
		while(itr.hasNext()) {
			saveCommands.add(itr.next());
		}
		saveQueue.add(saveCommands);
		writeToFile();
		requestFocus();
	}
	
	private void writeToFile() {
		ArrayList<Command> commands = saveQueue.peekLast();
		String newFile = String.format("save_%s.txt",new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()).toString());
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(newFile, true))) {
			for (Command c : commands) {
				bw.write(c.save() + "\n");
				bw.flush();
			}			
			bw.close();
			Window.addItem(newFile);
        }
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load last game action
	 */


	public void loadGame(String filename) {
		if (filename != null) {
			// TODO: DELETE FILE
			try {
				BufferedReader reader = new BufferedReader(new FileReader(filename));
				ArrayList<String> lines = new ArrayList<String>();
				String line = "";
				while((line = reader.readLine()) != null) {
				    lines.add(line);
				}
				reader.close();
				String[] data = lines.toArray(new String[]{});
				ArrayList<Command> loadCommands = new ArrayList<>();
				
				this.init();
				
				for (String s: data) {
					String[] parts = s.split(" ");
					switch(parts[0]) {
						case "Ball":
							ball.setPosition(Integer.parseInt(parts[2]), Integer.parseInt(parts[4]));
							MoveBallCommand c1 = new MoveBallCommand(this.ball);
							loadCommands.add(c1);
							break;
						case "ClockTickCommand":
							ClockTickCommand c2 = new ClockTickCommand(this.clock);
							loadCommands.add(c2);
							break;
						case "Brick":
							int id = Integer.parseInt(parts[2]);
							for (Brick b: brickList) {
								if (b.getBrickId() == id) {
									BrickCommand c3 = new BrickCommand(b);
									loadCommands.add(c3);
									break;
								}
							}
							break;
						case "ScoreBoard":
							ScoreCommand c4 = new ScoreCommand(this.scoreBoard);
							loadCommands.add(c4);
							break;
						case "Paddle":
							paddle.setPosition(Integer.parseInt(parts[2]), Integer.parseInt(parts[4]));
							MovePaddleCommand c5 = new MovePaddleCommand(paddle);
							loadCommands.add(c5);
							break;
						default:
							break;
					}
				}
				Collections.reverse(loadCommands);
				this.queue = new ArrayDeque<>(loadCommands); 
				requestFocus();
				revalidate();
				repaint();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	 
	// Abstract methods that need to be implemented
	@Override public void keyTyped(KeyEvent e) {}
	@Override public void keyReleased(KeyEvent e) {}

}
