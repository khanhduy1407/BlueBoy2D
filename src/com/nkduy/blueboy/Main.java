package com.nkduy.blueboy;

import com.nkduy.blueboy.main.GamePanel;

import javax.swing.*;
import java.util.Objects;

public class Main {

    public static JFrame window;
    public static GameInfo gameInfo;

    public static void main(String[] args) {
        window = new JFrame();
        gameInfo = new GameInfo();

        // This lets the window properly close when user clicks the close ("x") button.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        gameInfo.setShowVersion(true);
        gameInfo.setWindowWithTitle();
        new Main().setIcon();

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        if (gamePanel.fullScreenOn) {
            window.setUndecorated(true);
        }

        // Cause this Window to be sized to fix the preferred size and layout of
        // its subcomponents (= GamePanel).
        window.pack();

        // Not specify the location of the window.
        // = The window will be displayed at the center of the screen.
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }

    public void setIcon() {
        ImageIcon icon = new ImageIcon(Objects
                .requireNonNull(getClass().getClassLoader().getResource("player/boy/down_1.png")));
        window.setIconImage(icon.getImage());
    }
}
