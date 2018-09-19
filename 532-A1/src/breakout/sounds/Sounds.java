package breakout.sounds;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/*
 * References : http://www.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
 */
public enum Sounds {
	BALL_PADDLE("ball_hit_paddle.wav"),
	BALL_BRICK("ball_hit_brick.wav");
	private Clip clip;
	
	Sounds(String fileName){
		try {
			URL url = this.getClass().getClassLoader().getResource(fileName);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		if (clip.isRunning())
            clip.stop();
        clip.setFramePosition(0);
        clip.start();
	}
}
