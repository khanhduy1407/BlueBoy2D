package environment;

import main.GamePanel;

import java.awt.*;

/**
 * Lighting, Rain, Fogs, etc.
 */
public class EnvironmentManager {

    GamePanel gp;
    Lighting lighting;

    public EnvironmentManager(GamePanel gp) {
        this.gp = gp;
    }

    public void setup() {
        lighting = new Lighting(gp, 576);
    }

    public void draw(Graphics2D g2) {
        lighting.draw(g2);
    }
}
