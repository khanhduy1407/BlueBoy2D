package com.nkduy.blueboy.object;

import com.nkduy.blueboy.entity.Entity;
import com.nkduy.blueboy.main.GamePanel;

public class OBJ_Shield_Blue extends Entity {

    public static final String objName = "Blue Shield";

    public OBJ_Shield_Blue(GamePanel gp) {
        super(gp);

        type = type_shield;
        style_player = "/shield_blue";
        name = objName;
        down1 = setup("/objects/shield_blue", gp.tileSize, gp.tileSize);
        defenseValue = 2;
        description = "[" + name + "]\nA shiny blue shield.";
        price = 250;
    }
}
