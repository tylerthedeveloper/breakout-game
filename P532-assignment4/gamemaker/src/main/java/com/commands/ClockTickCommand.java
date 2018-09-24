package com.commands;

import com.components.Clock;

public class ClockTickCommand implements Command {

	Clock clock;
	int oldSeconds;
	int oldMinutes;

	/**
	 * Store the current state then update
	 */
	public ClockTickCommand(Clock clock) {
		this.clock = clock;		
		oldSeconds = clock.getSeconds();
		oldMinutes = clock.getMinutes();
		execute();
	}

	/**
	 * This command was created when the timer ticked, so we update the time accordingly
	 */
	@Override
	public void execute() {
		clock.performAction();
	}

	/**
	 * This command was created when the timer was un-ticked, so we return to previous state
	 */
	@Override
	public void undo() {
//		clock.setSeconds(oldSeconds);
//		clock.setMinutes(oldMinutes);
	}
	
//	public String save() {
//		return "ClockTickCommand Seconds= " + oldSeconds +" Minutes= "+oldMinutes+" Count= "+oldCount;
//	}

}
