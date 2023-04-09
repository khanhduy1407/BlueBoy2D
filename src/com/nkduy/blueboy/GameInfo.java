package com.nkduy.blueboy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameInfo {

    public String title = "Blue Boy Adventure";

    String gitSHA = getGitSHA();
    String shortGitSHA = gitSHA.substring(0, 7);

    int major = 3;
    int minor = 0;
    int patch = 0;
    String preRelease = "beta.0";
    String build = shortGitSHA;
    public String version = major + "." + minor + "." + patch + "-" + preRelease + "+" + build;
    public boolean showVersion = false;

    public GameInfo() { }

    public void setShowVersion(boolean showVersion) {
        this.showVersion = showVersion;
    }

    public void setWindowWithTitle() {
        if (!showVersion) {
            Main.window.setTitle(title);
        } else {
            Main.window.setTitle(title + " - " + version);
        }
    }

    public void setWindowWithSubtitle(String subtitle) {
        if (!showVersion) {
            Main.window.setTitle(title + " - " + subtitle);
        } else {
            Main.window.setTitle(title + " - " + subtitle  + " - " + version);
        }
    }

    private static String getGitSHA() {
        String command = "git rev-parse HEAD";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }
}
