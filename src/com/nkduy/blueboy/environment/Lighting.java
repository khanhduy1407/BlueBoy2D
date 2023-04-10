package com.nkduy.blueboy.environment;

import com.nkduy.blueboy.main.GamePanel;
import com.nkduy.blueboy.util.Area;
import com.nkduy.blueboy.util.DateState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting {

    GamePanel gp;
    BufferedImage darknessFilter;
    public int dayCounter;
    public float filterAlpha = 0f;

    // Day State
    public DateState dayState = DateState.DAY;

    public Lighting(GamePanel gp) {
        this.gp = gp;

        setLightSource();
    }

    public void setLightSource() {
        // Create a buffered image
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        if (gp.player.currentLight == null) {
            g2.setColor(new Color(0, 0, 0.1f, 0.97f));
        } else {
            // Get the center x and y of the light circle
            int centerX = gp.player.screenX + (gp.tileSize) / 2;
            int centerY = gp.player.screenY + (gp.tileSize) / 2;

            // Create a gradient effect within the light circle
            Color[] color = new Color[12];
            float[] fraction = new float[12];

            color[0] = new Color(0, 0, 0.1f, 0.1f);
            color[1] = new Color(0, 0, 0.1f, 0.42f);
            color[2] = new Color(0, 0, 0.1f, 0.52f);
            color[3] = new Color(0, 0, 0.1f, 0.61f);
            color[4] = new Color(0, 0, 0.1f, 0.69f);
            color[5] = new Color(0, 0, 0.1f, 0.76f);
            color[6] = new Color(0, 0, 0.1f, 0.82f);
            color[7] = new Color(0, 0, 0.1f, 0.87f);
            color[8] = new Color(0, 0, 0.1f, 0.91f);
            color[9] = new Color(0, 0, 0.1f, 0.92f);
            color[10] = new Color(0, 0, 0.1f, 0.93f);
            color[11] = new Color(0, 0, 0.1f, 0.94f);

            fraction[0] = 0f;
            fraction[1] = 0.4f;
            fraction[2] = 0.5f;
            fraction[3] = 0.6f;
            fraction[4] = 0.65f;
            fraction[5] = 0.7f;
            fraction[6] = 0.75f;
            fraction[7] = 0.8f;
            fraction[8] = 0.85f;
            fraction[9] = 0.9f;
            fraction[10] = 0.95f;
            fraction[11] = 1f;

            // Create a gradient paint settings for the light circle
            RadialGradientPaint gPaint =
                    new RadialGradientPaint(centerX, centerY, gp.player.currentLight.lightRadius, fraction, color);

            // Set the gradient data on g2
            g2.setPaint(gPaint);
        }

        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.dispose();
    }

    public void resetDay() {
        dayState = DateState.DAY;
        filterAlpha = 0f;
    }

    public void update() {
        if (gp.player.lightUpdated) {
            setLightSource();
            gp.player.lightUpdated = false;
        }

        // Check the state of the day
        if (dayState == DateState.DAY) {
            dayCounter++;

            if (dayCounter > 3600) { // 36.000s = 10m
                dayState = DateState.DUSK;
                dayCounter = 0;
            }
        }
        if (dayState == DateState.DUSK) {
            filterAlpha += 0.0005f; // 0.0001f x 10.000 = 1f, 10.000/60 = 166s

            if (filterAlpha > 1f) {
                filterAlpha = 1f;
                dayState = DateState.NIGHT;
            }
        }
        if (dayState == DateState.NIGHT) {
            dayCounter++;

            if (dayCounter > 36000) {
                dayState = DateState.DAWN;
                dayCounter = 0;
            }
        }
        if (dayState == DateState.DAWN) {
            filterAlpha -= 0.005f;

            if (filterAlpha < 0f) {
                filterAlpha = 0;
                dayState = DateState.DAY;
            }
        }
    }

    public void draw(Graphics2D g2) {
        if (gp.currentArea == Area.OUTSIDE) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        }
        if (gp.currentArea == Area.OUTSIDE || gp.currentArea == Area.DUNGEON) {
            g2.drawImage(darknessFilter, 0, 0, null);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // DEBUG
        String situation = "";

        switch (dayState) {
            case DAY -> situation = "Day";
            case DUSK -> situation = "Dust";
            case NIGHT -> situation = "Night";
            case DAWN -> situation = "Dawn";
        }

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(50f));
        g2.drawString(situation, 800, 500);
    }
}
