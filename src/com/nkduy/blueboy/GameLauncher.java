package com.nkduy.blueboy;

import com.nkduy.blueboy.main.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class GameLauncher extends JFrame implements ActionListener {

    JLabel title, version;
    JButton startButton;
    JPanel panel;

    public static GameInfo gameInfo;
    public static GamePanel gamePanel;

    public GameLauncher() {
        gameInfo = new GameInfo();
        gamePanel = new GamePanel();

        setTitle("Game Launcher");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        BufferedImage iconImage = null;
        try {
            iconImage = ImageIO.read(new File("res/player/boy/down_1.png"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        setIconImage(iconImage);

        panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        title = new JLabel("Welcome to Blue Boy 2D");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(title, constraints);

        startButton = new JButton("Start Game");
        startButton.addActionListener(this);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(startButton, constraints);

        version = new JLabel("v" + gameInfo.version);
        version.setFont(new Font("Arial", Font.PLAIN, 13));
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(version, constraints);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        ImageIcon icon = new ImageIcon("res/player/boy/down_1.png");
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(48, 48, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newImage);
        JOptionPane.showInputDialog(this, "Enter your username:", "Username Input", JOptionPane.PLAIN_MESSAGE, newIcon, null, null);
        dispose();
    }

    public static void main(String[] args) {
        new GameLauncher();
    }
}
