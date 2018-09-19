package breakout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import breakout.ScreenElements.Sprite;


public class Time implements Observable {
	
	private Timer timer;
	ArrayList<Sprite> observers = new ArrayList<>();
	private boolean running;

	Time(){
				
        ActionListener actionPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               notifyObservers();
            }
        };
         
        timer = new Timer(Constants.getTimerCount() ,(ActionListener) actionPerformer);
        startTimer();
	}
	
	@Override
	public void registerObserver(Sprite o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Sprite o) {
		observers.remove(observers.indexOf(o));
	}

	@Override
	public void notifyObservers() {
		for(Sprite observer: observers) {
			observer.update();
		}
	}
	
	public void startTimer() {
		running=true;
		timer.start();
	}
	
	public void stopTimer() {
		running=false;
		timer.stop();
	}
	public boolean isRunning() {
		return running;
	}
	
}
