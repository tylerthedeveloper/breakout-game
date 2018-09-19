package breakout.layout;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;

import breakout.LayoutChangeableJPanel;

public class BreakoutBoxLayout implements LayoutType
{

	@Override
	public void update(ArrayList<JButton> buttons, LayoutChangeableJPanel buttonPanel, JComboBox box) {
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));
		for( JButton b : buttons)
		{
			b.setFocusable(false);
			buttonPanel.add(b);
		}
		buttonPanel.add(box);
	}

}