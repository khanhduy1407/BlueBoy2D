package main;

import javax.swing.*;
import java.awt.*;

// This class inherits JPanel class
public class GamePanel extends JPanel {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        // If set to true, all the drawing from this component will be done in an offscreen painting buffer.
        // In short, enabling this can improve game's rendering performance
        this.setDoubleBuffered(true);
    }
}
