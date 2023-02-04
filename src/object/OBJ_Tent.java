package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity {

    GamePanel gp;

    public OBJ_Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Tent";
        down1 = setup("/objects/tent", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nYou can sleep until\nnext morning.";
        price = 300;
        stackable = true;
    }

    public boolean use(Entity entity) {
        //
    }
}
