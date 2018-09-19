package breakout.Commands;

/**
 * Command interface customized to support the application
 */
public interface Command {
		
	public void execute();

	public void undo();
	
	public String toString();
	
}
