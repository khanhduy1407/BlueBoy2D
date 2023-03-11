package com.nkduy.blueboy.object;

import com.nkduy.blueboy.entity.Entity;
import com.nkduy.blueboy.main.GamePanel;

public class OBJ_Boots extends Entity {

    public static final String objName = "Boots";

    public OBJ_Boots(GamePanel gp) {
        super(gp);

        name = objName;
        down1 = setup("/objects/boots", gp.tileSize, gp.tileSize);
    }
}
