package breakout;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Window extends JFrame {
	
	private static ScreenManager screenManager;
	
	
	public Window() {
	    super();
        setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(2,2,2,2);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setTitle("Break Out Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to stop the execution whenever the GUI is closed.
		this.setVisible(true); // to make the frame visible when executed.

		screenManager = new ScreenManager(); //instance of ScreenManager
		JButton pause = new JButton("  Pause  ");
		this.add(pause);
		pause.addActionListener(al -> {
			pause.setText(pause.getText().equals("  Pause  ")?"Resume":"  Pause  ");
			screenManager.pauseGame();
		});
		pause.setBounds(50, 200, 50, 30);
		pause.setVisible(true);
		
		JButton undo = new JButton("Undo");
		undo.setBounds(50, 250, 50, 30);
		undo.addActionListener(al -> {
			screenManager.undo();
		});
		undo.setVisible(true);
		

		JButton replay = new JButton("Replay");
		replay.setBounds(50, 250, 50, 30);
		replay.addActionListener(al -> {
			screenManager.replay();
		});
		replay.setVisible(true);

		JButton restart = new JButton("Restart");
		this.add(restart);
		restart.setBounds(50, 250, 50, 30);
		restart.addActionListener(al -> {
			pause.setText(pause.getText().equals("  Pause  ")?"Resume":"  Pause  ");
			screenManager.pauseGame();
//			screenManager=new ScreenManager();
			screenManager.init();
			screenManager.emptyQueue();
			screenManager.resumeGame();
			

			});
		restart.setVisible(true);


		
		JPanel buttons = new JPanel();

		
		buttons.add(pause);
		buttons.add(undo);
		buttons.add(replay);
		buttons.add(restart);
			
		this.add(buttons);


        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

 		this.getContentPane().add(buttons, gridBagConstraints);

        
        
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.9;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;


		this.getContentPane().add(screenManager, gridBagConstraints);
		this.addKeyListener(screenManager);
		screenManager.requestFocus();
		
	}
	
	public static void startGame() {
		new Window();
	}

}
