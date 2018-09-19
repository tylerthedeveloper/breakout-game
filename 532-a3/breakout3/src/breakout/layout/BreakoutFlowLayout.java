package breakout.layout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;

import breakout.LayoutChangeableJPanel;

public class BreakoutFlowLayout implements LayoutType
{

	@Override
	public void update(ArrayList<JButton> buttons,LayoutChangeableJPanel buttonPanel, JComboBox box) {
		buttonPanel.setLayout(new FlowLayout());
		for (JButton button : buttons) {
			buttonPanel.add(button);
		}
		buttonPanel.add(box);
	}

}
