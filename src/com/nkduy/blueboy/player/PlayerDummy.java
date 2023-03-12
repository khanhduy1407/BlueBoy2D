package com.nkduy.blueboy.player;

import com.nkduy.blueboy.entity.Entity;
import com.nkduy.blueboy.main.GamePanel;

public class PlayerDummy extends Entity {

    GamePanel gp;
    public static final String npcName = "Dummy";

    public PlayerDummy(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = npcName;

        getPlayerImage();
    }

    public void getPlayerImage() {
        up1 = setup("/player/" + gp.player.classPlayer + "/up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/" + gp.player.classPlayer + "/up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/" + gp.player.classPlayer + "/down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/" + gp.player.classPlayer + "/down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/" + gp.player.classPlayer + "/left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/" + gp.player.classPlayer + "/left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/" + gp.player.classPlayer + "/right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/" + gp.player.classPlayer + "/right_2", gp.tileSize, gp.tileSize);
    }
}
