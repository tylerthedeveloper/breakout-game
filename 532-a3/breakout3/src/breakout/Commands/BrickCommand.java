package breakout.Commands;

import breakout.Constants;
import breakout.ScreenController;
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

		for(int i=0;i<ScreenController.brickList.size();i++) {
			Brick b = ScreenController.brickList.get(i);
			if (brick.getBrickId() == b.getBrickId() ) {
				ScreenController.brickList.remove(i);
			}
		}
	}

	@Override
	public void undo() {
		ScreenController.brickList.add(brick);
	}
	
	public String save() {
		return "Brick BrickId= " + brick.getBrickId();
	}


}
