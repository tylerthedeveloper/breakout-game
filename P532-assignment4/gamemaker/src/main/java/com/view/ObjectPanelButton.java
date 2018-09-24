package com.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.infrastructure.ComponentType;
import com.infrastructure.ObjectListType;
import com.infrastructure.ObjectProperties;

@SuppressWarnings("serial")
public class ObjectPanelButton extends JButton implements ActionListener {
	
//	private String name;
	private WindowFrame windowFrame;
	private ObjectProperties selected = new ObjectProperties();
	private ComponentType componentType;
	
	JTextField vXField;
	JTextField vYField;
	JTextField widthField;
	JTextField heightField;
	JCheckBox canCollectField;
	
	JRadioButton collectible;
	JRadioButton event;
	JRadioButton action;
	ButtonGroup group;
	
	public ObjectPanelButton(ComponentType componentType, Color yellow, WindowFrame windowFrame) {

		addActionListener(this);
		setVisible(true);
		setAlignmentX(CENTER_ALIGNMENT);
		setAlignmentY(CENTER_ALIGNMENT);
		this.windowFrame = windowFrame;
		
		
		this.windowFrame.getFormPanel().selected = selected;
		
		this.componentType = componentType;
		if(componentType == ComponentType.BALL)
			setText("Ball");
		
		if(componentType == ComponentType.BRICK)
			setText("Brick");
		
		if(componentType == ComponentType.PADDLE)
			setText("Paddle");
		
		if(componentType == ComponentType.CLOCK)
			setText("Clock");
		
		if(componentType == ComponentType.FIRE)
			setText("Fire");
		
		if(componentType == ComponentType.BACKGROUND)
			setText("Background");
		
		if(componentType == ComponentType.SAVE)
			setText("Save");
		
		if(componentType == ComponentType.LOAD)
			setText("Load");
		
		if(componentType == ComponentType.PLAY)
			setText("Play");
		
		selected.setComponentType(componentType);

	}

	public void save() {
//		pause();
		try {
			String fileName = windowFrame.showSaveDialog();
			if(!fileName.isEmpty()) {
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			
			windowFrame.save(out);
//			out.writeObject(commandQueue);
			out.close();
			fileOut.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	public void load() {
//		pause();
//		commandQueue.clear();
		try {
			String fileName = windowFrame.showOpenDialog();
			if(!fileName.isEmpty()) {
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			
			windowFrame.load(in);
			
//			commandQueue.clear();
//			Deque<Command> loadCmdQueue = (Deque<Command>) in.readObject();
//			commandQueue.addAll(loadCmdQueue);
//			initCommands();
			in.close();
			fileIn.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		windowFrame.draw(null);
	}	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(componentType.equals(ComponentType.BACKGROUND))
		{
			setBackground();
		}
		
		else if(componentType.equals(ComponentType.PLAY))
		{
			
		}
		
		else if(componentType.equals(ComponentType.SAVE))
		{
			save();
		}
		
		else if(componentType.equals(ComponentType.LOAD))
		{
			load();
		}
		
		else
		{
			
			
			vXField = new JTextField(Integer.toString(selected.getVelX()) ,5);
			vYField = new JTextField(Integer.toString(selected.getVelY()) ,5);
		    widthField = new JTextField(Integer.toString(selected.getWidth()), 5);
			heightField = new JTextField(Integer.toString(selected.getHeight()) ,5);
			canCollectField = new JCheckBox("Can collect the collectibles");
			
			
			collectible=new JRadioButton("Collectible");
			event=new JRadioButton("Player object");
			action = new JRadioButton("Game Object");
			group=new ButtonGroup();
			group.add(collectible);
			group.add(event);
			group.add(action);
			
			JPanel myPanel = new JPanel();
			myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
			myPanel.add(Box.createVerticalStrut(15)); // a spacer
			myPanel.add(new JLabel("Velocity X: "));
			myPanel.add(vXField);
			myPanel.add(Box.createVerticalStrut(15)); // a spacer
			myPanel.add(new JLabel("Velocity Y: "));
			myPanel.add(vYField);
			myPanel.add(Box.createVerticalStrut(15)); // a spacer
			myPanel.add(new JLabel("Width: "));
			myPanel.add(widthField);
			myPanel.add(Box.createVerticalStrut(15)); // a spacer
			myPanel.add(new JLabel("Height: "));
			myPanel.add(heightField);

			myPanel.add(canCollectField);
			
			myPanel.add(event);
			myPanel.add(Box.createHorizontalStrut(15));
			
			myPanel.add(action);
	     
			myPanel.add(Box.createHorizontalStrut(15));
			
			myPanel.add(collectible);
	     
			
			int result1 = JOptionPane.showConfirmDialog(null, myPanel, 
	               "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
	      
			if (result1 == JOptionPane.OK_OPTION) {
				selected.setVelX(Integer.parseInt(vXField.getText()));
				selected.setVelY(Integer.parseInt(vYField.getText()));
				selected.setWidth(Integer.parseInt(widthField.getText()));
				selected.setHeight(Integer.parseInt(heightField.getText()));
				selected.setCanCollect(canCollectField.isSelected());
				int n = 1;
				for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements(); n++)
	            {
	                AbstractButton button = buttons.nextElement();
	                if (button.isSelected())
	                {
	                	String text = button.getText();
	                	if(text.equals("Collectible")) {
	                		selected.setObjectListType(ObjectListType.COLLECTIBLE);
//	                    	System.out.println("Game maker controller can collect = here ");

	                	}else if(text.equals("Player object")) {
	                		selected.setObjectListType(ObjectListType.EVENT);
	                		
	                	}else if(text.equals("Game Object")) {
	                		selected.setObjectListType(ObjectListType.ACTION);
	                		
	                	}
	                }
	            }				
				windowFrame.getFormPanel().setSelected(selected);
	      	}
		}
	}
	
	public void setBackground()
	{
		String imagePath = windowFrame.getFormPanel().fileExplorer();
		windowFrame.getGamePanel().setImage(imagePath);
	}
}
