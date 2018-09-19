package breakout;

import breakout.ScreenElements.Sprite;

public interface Observable {
	public void registerObserver(Sprite o);
	public void removeObserver(Sprite o);
	public void notifyObservers();
}
