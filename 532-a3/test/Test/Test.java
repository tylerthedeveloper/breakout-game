package Test;

import java.awt.Color;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import breakout.Collision;
import breakout.Constants;
import breakout.ScreenController;
import breakout.Commands.Command;
import breakout.ScreenElements.Ball;
import breakout.ScreenElements.Board;
import breakout.ScreenElements.Brick;
import breakout.ScreenElements.Paddle;
import junit.framework.TestCase;

public class Test extends TestCase 
{
	/*public void testCollisionAtInitialPosition()
	{
		Board board = new Board(Color.BLACK, Constants.getBoardPosX(), Constants.getBoardPosY(), Constants.getBoardWidth(), Constants.getBoardHeight());
		Ball ball=new Ball(Color.RED, Constants.getBallPosX(), Constants.getBallPosY(), Constants.getBallRadius());
		Paddle paddle = new Paddle(Color.GRAY, Constants.getPaddlePosX(), Constants.getPaddlePosY(), Constants.getPaddleWidth(), Constants.getPaddleHeight());
		ScreenController s=new ScreenController();

		Collision c = new Collision(ball,board);		
		s.add(ball);
		assertEquals(false,c.isElementCollidingWithBall(paddle));
	}

	public void testHasBrickCollidedWithBall()
	{
		Board board = new Board(Color.BLACK, Constants.getBoardPosX(), Constants.getBoardPosY(), Constants.getBoardWidth(), Constants.getBoardHeight());
		Ball ball=new Ball(Color.RED, Constants.getBoardPosX(), Constants.getBrickPosY(), Constants.getBallRadius());
		Brick brick = new Brick(1,Color.BLUE, Constants.getBoardPosX(), Constants.getBrickPosY(), Constants.getBrickWidth(), Constants.getBrickHeight());				

		ScreenController s=new ScreenController();
		Collision c = new Collision(ball,board);		
		s.add(ball);
		assertEquals(true,c.isElementCollidingWithBall(brick));
	}*/

	public void testIsBrickRemoved()
	{
		ArrayList<Brick> brickList = new ArrayList<>();
		
		Brick brick1 = new Brick(1,Color.BLUE, Constants.getBoardPosX(), Constants.getBrickPosY(), Constants.getBrickWidth(), Constants.getBrickHeight());				
		Brick brick2 = new Brick(1,Color.BLUE, Constants.getBoardPosX()+Constants.getBrickWidth(), Constants.getBrickPosY(), Constants.getBrickWidth(), Constants.getBrickHeight());				
		Brick brick3 = new Brick(1,Color.BLUE, Constants.getBoardPosX()+Constants.getBrickWidth()+Constants.getBrickWidth(), Constants.getBrickPosY(), Constants.getBrickWidth(), Constants.getBrickHeight());				

		brickList.add(brick1);
		brickList.add(brick2);
		brickList.add(brick3);
		
		ScreenController s=new ScreenController();
		Board board = new Board(Color.BLACK, Constants.getBoardPosX(), Constants.getBoardPosY(), Constants.getBoardWidth(), Constants.getBoardHeight());
		Ball ball=new Ball(Color.RED, Constants.getBoardPosX(), Constants.getBrickPosY(), Constants.getBallRadius());
		Collision c = new Collision(ball,board);		
		s.add(ball);
		
		s.setBrickList(brickList);
		s.setCollision(c);
		
		assertEquals(brick1,s.getCollisionBrick());
	}
	public void testIsBrickRemovedNegative()
	{
		ArrayList<Brick> brickList = new ArrayList<>();
		
		Brick brick1 = new Brick(1,Color.BLUE, Constants.getBoardPosX(), Constants.getBrickPosY(), Constants.getBrickWidth(), Constants.getBrickHeight());				
		Brick brick2 = new Brick(1,Color.BLUE, Constants.getBoardPosX()+Constants.getBrickWidth(), Constants.getBrickPosY(), Constants.getBrickWidth(), Constants.getBrickHeight());				
		Brick brick3 = new Brick(1,Color.BLUE, Constants.getBoardPosX()+Constants.getBrickWidth()+Constants.getBrickWidth(), Constants.getBrickPosY(), Constants.getBrickWidth(), Constants.getBrickHeight());				

		brickList.add(brick1);
		brickList.add(brick2);
		brickList.add(brick3);
		
		ScreenController s=new ScreenController();
		Board board = new Board(Color.BLACK, Constants.getBoardPosX(), Constants.getBoardPosY(), Constants.getBoardWidth(), Constants.getBoardHeight());
		Ball ball=new Ball(Color.RED, Constants.getBoardPosX(), Constants.getBrickPosY()+500, Constants.getBallRadius());
		Collision c = new Collision(ball,board);		
		s.add(ball);
		
		s.setBrickList(brickList);
		s.setCollision(c);
		
		assertEquals(null,s.getCollisionBrick());
	}
}
