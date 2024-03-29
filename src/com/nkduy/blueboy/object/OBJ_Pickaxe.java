package com.nkduy.blueboy.object;

import com.nkduy.blueboy.entity.Entity;
import com.nkduy.blueboy.main.GamePanel;

public class OBJ_Pickaxe extends Entity {

    public static final String objName = "Pickaxe";

    public OBJ_Pickaxe(GamePanel gp) {
        super(gp);

        type = type_pickaxe;
        style_player = "/pick";
        name = objName;
        down1 = setup("/objects/pickaxe", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nYou will dig it!";
        price = 75;
        knockBackPower = 10;
        motion1_duration = 10;
        motion2_duration = 20;
    }
}
