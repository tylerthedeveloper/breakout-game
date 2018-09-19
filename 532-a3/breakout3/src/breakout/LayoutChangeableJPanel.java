package breakout;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import breakout.layout.LayoutType;

public class LayoutChangeableJPanel extends JPanel
{
	private LayoutType type;

	public LayoutType getType() {
		return type;
	}

	public void setType(LayoutType type) {
		this.type = type;
	}
	
	public void changeLayout(ArrayList<JButton> buttons,LayoutChangeableJPanel buttonPanel, JComboBox box)
	{
		type.update(buttons,buttonPanel, box);
		this.revalidate();
		this.repaint();
	}
	
}
