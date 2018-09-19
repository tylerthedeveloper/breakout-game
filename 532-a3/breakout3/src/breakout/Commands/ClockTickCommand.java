package breakout.Commands;

import breakout.ScreenElements.Clock;

public class ClockTickCommand implements Command {

	Clock clock;
	int oldSeconds;
	int oldMinutes;
	int oldCount;

	/**
	 * Store the current state then update
	 */
	public ClockTickCommand(Clock clock) {
		this.clock=clock;		
		oldSeconds = clock.getSeconds();
		oldMinutes = clock.getMinutes();
		oldCount = clock.getCount();
		execute();
	}

	/**
	 * This command was created when the timer ticked, so we update the time accordingly
	 */
	@Override
	public void execute() {
		clock.execute();
	}

	/**
	 * This command was created when the timer was un-ticked, so we return to previous state
	 */
	@Override
	public void undo() {
		clock.setSeconds(oldSeconds);
		clock.setMinutes(oldMinutes);
		clock.setCount(oldCount);
	}
	
	public String save() {
		return "ClockTickCommand Seconds= " + oldSeconds +" Minutes= "+oldMinutes+" Count= "+oldCount;
	}

}
