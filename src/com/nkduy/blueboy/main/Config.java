package com.nkduy.blueboy.main;

import com.nkduy.blueboy.GameLauncher;
import com.nkduy.blueboy.Main;

import java.io.*;

public class Config {

    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            // Username
            bw.write("Username: " + GameLauncher.username);
            bw.newLine();

            // Full screen
            if (gp.fullScreenOn) {
                bw.write("Fullscreen: on");
            } else {
                bw.write("Fullscreen: off");
            }
            bw.newLine();

            // Music volume
            bw.write("Music: " + gp.music.volumeScale);
            bw.newLine();

            // SE volume
            bw.write("SE: " + gp.se.volumeScale);
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

            // Username
            GameLauncher.username = s.split("Username: ")[1];
            Main.username = GameLauncher.username;
            if (!GameLauncher.username.equals(" ")) {
                GameLauncher.isLogged = true;
            }

            // Full screen
            s = br.readLine();
            if (s.equals("Fullscreen: on")) {
                gp.fullScreenOn = true;
            }
            if (s.equals("Fullscreen: off")) {
                gp.fullScreenOn = false;
            }

            // Music volume
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s.split("Music: ")[1]);

            // SE volume
            s = br.readLine();
            gp.se.volumeScale = Integer.parseInt(s.split("SE: ")[1]);

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
