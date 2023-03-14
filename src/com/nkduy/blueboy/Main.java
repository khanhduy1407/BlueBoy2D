package com.nkduy.blueboy;

import com.nkduy.blueboy.main.GamePanel;

import javax.swing.*;
import java.io.File;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static JFrame window;
    public static GameInfo gameInfo;
    public static String username = " ";

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        GamePanel gamePanel = new GamePanel();
        window = new JFrame();
        gameInfo = new GameInfo();

        System.out.println("Blue Boy 2D v" + gameInfo.version + "\n");

        File f = new File("config.txt");
        if (!f.exists() && f.isDirectory()) {
            gamePanel.config.saveConfig();
        }
        TimeUnit.SECONDS.sleep(2);
        gamePanel.config.loadConfig();

        if (username.equals(" ")) {
            do {
                System.out.print("Enter username (10 characters): ");
                username = sc.nextLine();
            } while (username.length() <= 0 || username.length() > 10);
            gamePanel.config.saveConfig();
            System.out.println("Welcome " + username + ", wish you happy gaming!!!");
        } else {
            System.out.println("Welcome back " + username + "!!!");
        }
        TimeUnit.SECONDS.sleep(2);

        // This lets the window properly close when user clicks the close ("x") button.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

//        gameInfo.setShowVersion(true);
        gameInfo.setWindowWithTitle();
        new Main().setIcon();

        window.add(gamePanel);

//        gamePanel.config.loadConfig();
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
