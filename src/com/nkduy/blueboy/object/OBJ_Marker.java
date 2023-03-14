package com.nkduy.blueboy.object;

import com.nkduy.blueboy.entity.Entity;
import com.nkduy.blueboy.main.GamePanel;

public class OBJ_Marker extends Entity {

    public static final String objName = "Marker";

    public OBJ_Marker(GamePanel gp) {
        super(gp);

        type = type_marker;
        name = objName;
        down1 = setup("/objects/marker", gp.tileSize, gp.tileSize);
    }
}
