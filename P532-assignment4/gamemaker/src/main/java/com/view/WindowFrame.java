package com.view;

//import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.infrastructure.Constants;
import com.infrastructure.IComposite;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class WindowFrame extends JFrame implements IComposite {
	private ArrayList<IComposite> compositeList;

	private FormPanel formPanel;
	private GamePanel gamePanel;
	private StaticPanel staticPanel;
	private JFileChooser fileChooser;
	private MainPanel mainPanel;

	public MainPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public WindowFrame() {
		super();
		this.compositeList = new ArrayList<IComposite>();
		initializeUI();
	}

	
	public String showOpenDialog() {
		try {
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("serialize file","ser"));
		int rVal = fileChooser.showOpenDialog(gamePanel);
	    if (rVal == JFileChooser.APPROVE_OPTION) {
	    	String name = fileChooser.getSelectedFile().toString();
	    	return name;
	    }
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
		return "";
	}
	
	public String showSaveDialog() {
		try {
			fileChooser = new JFileChooser();
			fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
			fileChooser.setFileFilter(new FileNameExtensionFilter("serialize file","ser"));
			int rVal = fileChooser.showSaveDialog(this);
		      if (rVal == JFileChooser.APPROVE_OPTION) {
		        String name = fileChooser.getSelectedFile().toString();
		    	if (!name.endsWith(".ser"))
		    	        name += ".ser";
		    	return name;
		      }
		} catch (Exception e) {
			e.printStackTrace();
		}
	     return "";
	}

	
	private void initializeUI() {
		setSize(Constants.FRAME_WIDTH,Constants.FRAME_HEIGHT);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}
	
	public void setVisible() {
		super.setVisible(true);
	}
	
	public void draw(Graphics g) {
		for(IComposite composite : compositeList) {
			composite.draw(g);
		}
	}

	public ArrayList<IComposite> getCompositeList() {
		return compositeList;
	}
	
	public void addComponent(IComposite composite) {
		compositeList.add(composite);
		this.add((JPanel)composite);
	}

	public void removeComponent(IComposite composite) {
		// TODO Auto-generated method stub
		compositeList.remove(composite);
	}
	
	public FormPanel getFormPanel() {
		System.out.println("In getform panel");
		return formPanel;
	}
	

	public void setFormPanel(FormPanel formPanel) {
		this.formPanel = formPanel;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public StaticPanel getStaticPanel() {
		System.out.println("In getform panel");
		
		return staticPanel;
	}

	public void setStaticPanel(StaticPanel staticPanel) {
		this.staticPanel = staticPanel;
	}


	@Override
	public void save(ObjectOutputStream op) {
		for(IComposite composite : compositeList) {
			composite.save(op);
		}	
	}

	@Override
	public void load(ObjectInputStream ip) {
		for(IComposite composite : compositeList) {
			composite.load(ip);
		}	
	}
}
