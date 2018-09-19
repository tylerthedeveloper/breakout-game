package breakout.Commands;

import breakout.Constants;
import breakout.ScreenManager;
import breakout.Commands.Command;
import breakout.ScreenElements.Brick;

public class BrickCommand implements Command {

	Brick brick;
	public BrickCommand(Brick brick) {
		this.brick=brick;
		execute();
	}

	@Override
	public void execute() {

		for(int i=0;i<ScreenManager.brickList.size();i++) {
			Brick b = ScreenManager.brickList.get(i);
			if (brick.getBrickId() == b.getBrickId() ) {
				ScreenManager.brickList.remove(i);
			}
		}
	}

	@Override
	public void undo() {
		ScreenManager.brickList.add(brick);
	}
	
	public String toString() {
		return "Brick oldPositionX=" + brick.getX() +" oldPositionY= "+brick.getY();
	}


}
