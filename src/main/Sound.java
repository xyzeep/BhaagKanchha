package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	Clip clip; // we use this to open audio file
	URL[] soundURL = new URL[30]; // we use this url array to store the file paths of the audio files

	public Sound() {

		soundURL[0] = getClass().getResource("/sound/bhagKanchha.wav");
		soundURL[1] = getClass().getResource("/sound/jump.wav");
		soundURL[2] = getClass().getResource("/sound/land.wav");
		soundURL[3] = getClass().getResource("/sound/potion.wav");
		soundURL[4] = getClass().getResource("/sound/levelFinish.wav");
		soundURL[5] = getClass().getResource("/sound/cursor.wav");
		soundURL[6] = getClass().getResource("/sound/hurt.wav");

	}

	public void setFile(int i) {
		try {

			// this is the format to open audio files
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		clip.start();
	}

	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);

	}

	public void stop() {
		clip.stop();
	}
}
