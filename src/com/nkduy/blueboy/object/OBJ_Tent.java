package com.nkduy.blueboy.object;

import com.nkduy.blueboy.entity.Entity;
import com.nkduy.blueboy.main.GamePanel;
import com.nkduy.blueboy.state.GameState;

public class OBJ_Tent extends Entity {

    GamePanel gp;

    public static final String objName = "Tent";

    public OBJ_Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = objName;
        down1 = setup("/objects/tent", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nYou can sleep until\nnext morning.";
        price = 300;
        stackable = true;
    }

    public boolean use(Entity entity) {
        gp.gameState = GameState.SLEEP;
        gp.playSE(14);
        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;
        gp.player.getSleepingImage(down1);
        return true;
    }
}
