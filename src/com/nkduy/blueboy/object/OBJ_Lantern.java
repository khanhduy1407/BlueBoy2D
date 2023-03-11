package com.nkduy.blueboy.object;

import com.nkduy.blueboy.entity.Entity;
import com.nkduy.blueboy.main.GamePanel;

public class OBJ_Lantern extends Entity {

    public static final String objName = "Lantern";

    public OBJ_Lantern(GamePanel gp) {
        super(gp);

        type = type_light;
        name = objName;
        down1 = setup("/objects/lantern", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIlluminates your\nsurroundings.";
        price = 200;
        lightRadius = 350;
    }
}
