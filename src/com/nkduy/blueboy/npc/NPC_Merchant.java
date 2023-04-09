package com.nkduy.blueboy.npc;

import com.nkduy.blueboy.entity.Entity;
import com.nkduy.blueboy.main.GamePanel;
import com.nkduy.blueboy.object.*;
import com.nkduy.blueboy.util.GameState;

import java.util.Random;

public class NPC_Merchant extends Entity {

    GamePanel gp;

    public NPC_Merchant(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_npc;
        direction = "down";
        speed = 1;

        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        setDialogue();
        setItems();
    }

    public void getImage() {
        up1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "He he, so you found me.\nI have some good stuff.\nDo you want to trade?";
        dialogues[1][0] = "Come again, hehe!";
        dialogues[2][0] = "You need more coin to buy that!";
        dialogues[3][0] = "You cannot carry any more!";
        dialogues[4][0] = "You cannot sell an equipped item!";
    }

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 120) { // 2 second
            Random random = new Random();
            int i = random.nextInt(100) + 1; // pick up a number from 1 to 100

            if (i <= 25) { direction = "up"; }
            if (i > 25 && i <= 50) { direction = "down"; }
            if (i > 50 && i <= 75) { direction = "left"; }
            if (i > 75) { direction = "right"; }

            actionLockCounter = 0;
        }
    }

    public void setItems() {
        inventory.add(new OBJ_Potion_Red(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Sword_Normal(gp));
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Shield_Wood(gp));
        inventory.add(new OBJ_Shield_Blue(gp));
    }

    public void speak() {
        facePlayer();
        gp.gameState = GameState.TRADE;
        gp.ui.npc = this;
    }
}
