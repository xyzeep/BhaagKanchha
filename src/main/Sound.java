package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

	Clip clip; // we use this to open audio file
	URL[] soundURL = new URL[30]; // we use this url array to store the file paths of the audio files
	FloatControl fc;
	int volumeScale = 3;
	float volume;

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
			fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); // for volume
			// the float control accepts numbers between -80F to 6F
			checkVolume();

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

	public void checkVolume() {
		switch (volumeScale) {
		case 0:
			volume = -80F;
			break; // no sound
		case 1:
			volume = -20F;
			break;
		case 2:
			volume = -12F;
			break;
		case 3:
			volume = -5F;
			break;
		case 4:
			volume = 1F;
			break;
		case 5:
			volume = 6F;
			break;
		// why are the values of volume not evenly separated, you might ask
		// but it's just how the float control works
		// this works exponentially, lesser the numbers lesser the difference
		}

		fc.setValue(volume);
	}
}
