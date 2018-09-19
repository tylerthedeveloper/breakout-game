package breakout;

//import java.awt.Color;
import java.awt.event.KeyListener;
//import java.util.ArrayList;

import javax.swing.JFrame;




public class Window extends JFrame{
	
	private final int SCREEN_WIDTH = Constants.getSCREEN_WIDTH();
	private final int SCREEN_HEIGHT = Constants.getSCREEN_HEIGHT();	
	private static ScreenManager screenManager;

	
	public Window() {
	    super();

	    // Initialize screen 
	    this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		this.setTitle("Break Out Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to stop the execution whenever the GUI is closed.
		this.setVisible(true); // to make the frame visible when executed.

		screenManager = new ScreenManager(); //instance of ScreenManager
		
		this.getContentPane().add(screenManager);
		this.addKeyListener(screenManager);
		
	}
	
	public static void startGame() {
		new Window();
	}



}
