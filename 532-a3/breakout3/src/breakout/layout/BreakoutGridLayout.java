package breakout.layout;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;

import breakout.LayoutChangeableJPanel;

public class BreakoutGridLayout implements LayoutType
{

	@Override
	public void update(ArrayList<JButton> buttons,LayoutChangeableJPanel buttonPanel, JComboBox box) {
		buttonPanel.setLayout(new GridLayout(3,4,10,100));
		for (JButton button : buttons)
		{
			buttonPanel.add(button);
		}
		buttonPanel.add(box);		
	}
}