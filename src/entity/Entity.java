package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This stores variables that will be used in player, monster and NPC classes.
 */
public class Entity {

    public int worldX, worldY;
    public int speed;

    // It describes an Image with an accessible buffer of image data
    // (We use this to store our image files)
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
}
