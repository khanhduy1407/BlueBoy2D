package com.nkduy.blueboy;

import com.nkduy.blueboy.main.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class GameLauncher extends JFrame implements ActionListener {

    JLabel title, version;
    JButton startButton;
    JPanel panel;

    public static GameInfo gameInfo;
    public static GamePanel gamePanel;
    public static String username = " ";

    static boolean startGame = false;

    public GameLauncher() {
        gameInfo = new GameInfo();
        gamePanel = new GamePanel();

        File f = new File("config.txt");
        if (!f.exists() && f.isDirectory()) {
            gamePanel.config.saveConfig();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
            gamePanel.config.loadConfig();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

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
        startButton.setFont(new Font("Arial", Font.BOLD, 15));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(45, 87, 223));
        startButton.setOpaque(true);
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
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

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        int windowWidth = getWidth();
        int windowHeight = getHeight();
        int windowX = (screenWidth - windowWidth) / 2;
        int windowY = (screenHeight - windowHeight) / 2;

        setLocation(windowX, windowY);
    }

    public void actionPerformed(ActionEvent e) {
        if (username.equals(" ")) {
            UIManager.put("OptionPane.background", new Color(38, 38, 38));
            UIManager.put("Panel.background", new Color(38, 38, 38));
            UIManager.put("OptionPane.messageForeground", Color.WHITE);
            UIManager.put("TextField.background", Color.BLACK);
            UIManager.put("TextField.foreground", Color.WHITE);
            UIManager.put("Button.background", new Color(45, 87, 223));
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.border", BorderFactory.createEmptyBorder(5, 10, 5, 10));

            ImageIcon icon = new ImageIcon("res/player/boy/down_1.png");
            Image image = icon.getImage();
            Image newImage = image.getScaledInstance(48, 48, java.awt.Image.SCALE_SMOOTH);
            ImageIcon newIcon = new ImageIcon(newImage);
            do {
                username = (String) JOptionPane.showInputDialog(this, "Enter your username (10 characters):", "Input", JOptionPane.PLAIN_MESSAGE, newIcon, null, null);

                if (username == null) {
                    username = " ";
                    break;
                }
            } while (username.isEmpty() || username.length() > 10);
            gamePanel.config.saveConfig();
            System.out.println("Welcome " + username + ", wish you happy gaming!!!");
        } else {
            System.out.println("Welcome back " + username + "!!!");
        }

        dispose();
    }

    public static void main(String[] args) {
        new GameLauncher();
    }
}
