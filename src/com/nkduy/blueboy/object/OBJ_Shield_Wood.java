package com.nkduy.blueboy.object;

import com.nkduy.blueboy.entity.Entity;
import com.nkduy.blueboy.main.GamePanel;

public class OBJ_Shield_Wood extends Entity {

    public static final String objName = "Wood Shield";

    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);

        type = type_shield;
        style_player = "/guard";
        name = objName;
        down1 = setup("/objects/shield_wood", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nMade by wood.";
        price = 35;
    }
}
