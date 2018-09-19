package breakout;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import breakout.layout.BreakoutGridLayout;
import breakout.layout.BreakoutBoxLayout;
import breakout.layout.BreakoutFlowLayout;


public class Window extends JFrame {

	private ArrayList<JButton> buttons; // = new ArrayList<JButton>();
	private static ScreenController screenManager;
	private LayoutChangeableJPanel buttonPanel;
	private ArrayList<String> filenames;
	private static JComboBox<String> load;

	public Window() {
		super();
		setLayout(new GridBagLayout());

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(2,2,2,2);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setTitle("Break Out Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to stop the execution whenever the GUI is closed.
		this.setVisible(true); // to make the frame visible when executed.

		screenManager = new ScreenController(); //instance of ScreenManager

		buttons = new ArrayList<JButton>();
		buttonPanel = new LayoutChangeableJPanel();
		buttonPanel.setPreferredSize(new Dimension(Constants.getBUTTON_PANEL_WIDTH(),Constants.getBUTTON_PANEL_HEIGHT()));
		
		filenames = getFileNames();
		filenames.add(0, "Load");
		initButtons();
		
		gridBagConstraints.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints.weightx = 0.1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		this.getContentPane().add(buttonPanel, gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 0.9;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;

		this.getContentPane().add(screenManager, gridBagConstraints);
		this.addKeyListener(screenManager);
		screenManager.requestFocus();

	}

	public void changeLayout() {
		
		if(buttonPanel.getType() instanceof BreakoutFlowLayout) {
			this.buttonPanel.setType(new BreakoutGridLayout());
		}
		
		else if(buttonPanel.getType() instanceof BreakoutGridLayout) {
			this.buttonPanel.setType(new BreakoutBoxLayout());
		}

		else if(buttonPanel.getType() instanceof BreakoutBoxLayout) {
			this.buttonPanel.setType(new BreakoutFlowLayout());
		}
		
		buttonPanel.changeLayout(this.buttons,buttonPanel,load);
		
		this.getContentPane().revalidate();
		this.getContentPane().repaint();
		screenManager.requestFocus();
	}


	public void initButtons() {
		JButton pause = new JButton("Pause");

		pause.addActionListener(al -> {
			pause.setText(pause.getText().equals("Pause")?"Resume":"Pause");
			screenManager.pauseGame();
		});
		this.buttons.add(pause);

		JButton undo = new JButton("Undo");
		undo.addActionListener(al -> {
			screenManager.undo();
		});
		this.buttons.add(undo);

		JButton replay = new JButton("Replay");
		replay.addActionListener(al -> {
			screenManager.replay();
		});
		this.buttons.add(replay);

		JButton restart = new JButton("Restart");
		restart.addActionListener(al -> {
			pause.setText(pause.getText().equals("Pause")?"Resume":"Pause");
			screenManager.pauseGame();
			screenManager.init();
			screenManager.emptyQueue();
			screenManager.resumeGame();
		});
		this.buttons.add(restart);

		JButton save = new JButton("Save");
		save.addActionListener(al -> {
			screenManager.saveGame();
		});
		this.buttons.add(save);

		load = new JComboBox<> () {

            @Override
            public Dimension getMaximumSize() {
                Dimension max = super.getMaximumSize();
                max.height = 40;
                return max;
            }

        };
		for(String name: filenames) {
			load.addItem(name);
		}
		load.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = ((JComboBox) e.getSource()).getSelectedItem().toString();
				if (!name.equals("Load")) {
					screenManager.loadGame(name);
				}
			}
		});
		

		JButton changeLayout = new JButton("Change Layout");
		changeLayout.addActionListener(al -> {

			changeLayout();
		});
		this.buttons.add(changeLayout);

		for (JButton button : this.buttons) {
			button.setVisible(true);
			this.buttonPanel.add(button);
		}
		this.buttonPanel.add(load);
		buttonPanel.setType(new BreakoutFlowLayout());
		buttonPanel.changeLayout(this.buttons,buttonPanel,load);
	}
	
	private boolean isTxt(String fileName) 
	{
		int index = fileName.lastIndexOf(".");
		if (index == -1) return false;
		String extension = fileName.substring(index);
		if (extension.equals(".txt")) {
			return true;
		}
		return false;
	}
	
	public ArrayList<String> getFileNames() {
		File folder = new File(".");
		ArrayList<String> savedFiles = new ArrayList<>();
		for (File file: folder.listFiles()) {
			String name = file.getName();
			if (isTxt(name)) {
				savedFiles.add(name);
			}
	    }
		return savedFiles;
	}
	
	public static void addItem(String name) {
		load.addItem(name);
	}
	public static void startGame() {
		new Window();
	}

}
