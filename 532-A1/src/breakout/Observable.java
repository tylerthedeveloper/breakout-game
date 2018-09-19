package breakout;

import breakout.ScreenElements.Observer;

public interface Observable{
	public void registerObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObservers();
}
