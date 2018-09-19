package breakout;

import javax.swing.SwingUtilities;


public class App {
	
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
			
				Window.startGame();		
			}
		});
		// todo: create a new screen instance
		
	}
}
