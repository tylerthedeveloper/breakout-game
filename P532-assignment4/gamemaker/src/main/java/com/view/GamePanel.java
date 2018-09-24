package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.controller.GameMakerController;
import com.infrastructure.AbstractComponent;
import com.infrastructure.Constants;
import com.infrastructure.IComposite;
import com.infrastructure.IPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements IComposite, IPanel	 {

	private ArrayList<AbstractComponent> compositeList;
	private BufferedImage image;

	public GamePanel() {
		compositeList = new ArrayList<>();
		setBorder( BorderFactory.createLineBorder(Color.blue));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMaximumSize(new Dimension(Constants.GAME_PANEL_WIDTH, Constants.GAME_PANEL_HEIGHT));
		setMinimumSize(new Dimension(Constants.GAME_PANEL_WIDTH, Constants.GAME_PANEL_HEIGHT));
		setPreferredSize(new Dimension(Constants.GAME_PANEL_WIDTH, Constants.GAME_PANEL_HEIGHT));	
	}
	
	public ArrayList<AbstractComponent> getComponentList(){
		return(this.compositeList);
	}
	
	public void addControllerListener(GameMakerController controller) {
		addMouseListener(controller);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(image != null)
		{
			g.drawImage(image, 0, 0, this);
		}
		System.out.println("Length of composite list: " + compositeList.size());
		for(AbstractComponent composite : compositeList) {
			System.out.println(composite);
			if (composite.getVisibility())
				composite.draw(g);
		}
	}
	
	public void draw(Graphics g) {
//        revalidate();
		repaint();
	}

	public void addComponent(AbstractComponent abstractComponent) {
		compositeList.add(abstractComponent);
	}

	public void removeComponent(AbstractComponent abstractComponent) {
		compositeList.remove(abstractComponent);
	}
	
	public void setImage(String path)
	{
		try {
			image = ImageIO.read(new File(path));
			image = resize(image, Constants.GAME_PANEL_WIDTH, Constants.GAME_PANEL_HEIGHT);
			draw(null);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage resize(BufferedImage img, int width, int height)
	{
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		
		return resized;
	}
	

	@Override
	public void addComponent(IComposite composite) throws Exception {
		throw new Exception();
	}

	@Override
	public void removeComponent(IComposite composite) throws Exception {
		throw new Exception();	
	}

	@Override
	public void save(ObjectOutputStream op) {
		/*
		for (AbstractComponent abstractComponent: compositeList) {
			abstractComponent.save(op);
		}
		*/
		try {
			op.writeObject(compositeList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void load(ObjectInputStream ip) {
		try {
			compositeList = (ArrayList<AbstractComponent>) ip.readObject();
		} catch (java.lang.ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		while (true) {
			AbstractComponent abstractComponent = () ip.readObject();
		}
		*/
		/*
		
		
		try {
			Ball obj = (Ball)ip.readObject();
			return obj;
		} catch (ClassNotFoundException | IOException e) {
			log.error(e.getMessage());
		}
		*/
	}

}
