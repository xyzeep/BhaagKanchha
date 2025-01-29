package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
	GamePanel gp;

	public Config(GamePanel gp) {
		this.gp = gp;
	}

	public void saveConfig() {

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
			// current logged in user
			if (gp.currentUserID != null) {
				bw.write("currentUserID: " + gp.currentUserID);
			} else{
				bw.write("currentUserID: null");
			}
			bw.newLine();

			// full screen setting
			if (gp.fullScreenOn) {
				bw.write("FullScreen: On");
			} else if (!gp.fullScreenOn) {
				bw.write("FullScreen: Off");
			}
			bw.newLine();

			// music volume
			bw.write(String.valueOf("Music: " + gp.music.volumeScale));
			bw.newLine();

			// Sound effects volume
			bw.write("Sound: " + String.valueOf(gp.se.volumeScale));
			bw.newLine();

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void loadConfig() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("config.txt"));

			String s = br.readLine();

			// current user
			String userID = s.split(":")[1].trim(); // only get ID
			if (!userID.equals("null")) {
				gp.currentUserID = userID;
			}

			// full screen
			s = br.readLine();
			if (s.equals("FullScreen: On")) {
				gp.fullScreenOn = true;
			}

			else if (s.equals("FullScreen: Off")) {
				gp.fullScreenOn = false;
			}

			// music volume
			s = br.readLine().split(":")[1].trim(); // only get the number part
			gp.music.volumeScale = Integer.parseInt(s);

			// sound effects volume
			s = br.readLine().split(":")[1].trim();
			gp.se.volumeScale = Integer.parseInt(s);

			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
