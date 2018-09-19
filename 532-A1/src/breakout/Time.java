package breakout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import breakout.ScreenElements.Observer;


public class Time implements Observable {
	private Timer timer;
	ArrayList<Observer> observers = new ArrayList<>();

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
	public void registerObserver(Observer o) {
		
		// TODO Auto-generated method stub
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub
		observers.remove(observers.indexOf(o));
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		for(Observer observer: observers) {
			observer.update();
		}
	}
	
	public void startTimer() {
		timer.start();
	}
	
	public void stopTimer() {
		timer.stop();
	}

	
}
