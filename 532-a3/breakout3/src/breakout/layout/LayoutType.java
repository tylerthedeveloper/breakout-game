package breakout.layout;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;

import breakout.LayoutChangeableJPanel;

public interface LayoutType 
{
	public void update(ArrayList<JButton> buttons,LayoutChangeableJPanel buttonPanel, JComboBox box);
}
