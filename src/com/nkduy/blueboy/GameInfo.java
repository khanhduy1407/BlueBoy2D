package com.nkduy.blueboy;

public class GameInfo {

    public String title = "Blue Boy Adventure";

    int major = 3;
    int minor = 0;
    int patch = 0;
    String preRelease = "alpha.0";
    String build = "b0";
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
}
