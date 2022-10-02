package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        // This lets the window properly close when user clicks the close ("x") button.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Adventure");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // *pack(): Cause this Window to be sized to fix the preferred size and layout of
        // its subcomponents (= GamePanel).
        window.pack();

        // Not specify the location of the window.
        // = The window will be displayed at the center of the screen.
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
