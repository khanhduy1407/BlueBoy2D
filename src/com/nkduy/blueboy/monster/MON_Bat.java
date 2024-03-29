package com.nkduy.blueboy.monster;

import com.nkduy.blueboy.entity.Entity;
import com.nkduy.blueboy.main.GamePanel;
import com.nkduy.blueboy.object.OBJ_Coin_Bronze;
import com.nkduy.blueboy.object.OBJ_Heart;
import com.nkduy.blueboy.object.OBJ_ManaCrystal;

import java.util.Random;

public class MON_Bat extends Entity {

    GamePanel gp;

    public MON_Bat(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Bat";
        defaultSpeed = 4;
        speed = defaultSpeed;
        maxLife = 2;
        life = maxLife;
        attack = 1;
        defense = 0;
        exp = 2;

        solidArea.x = 3;
        solidArea.y = 15;
        solidArea.width = 42;
        solidArea.height = 21;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        // using two images (down 1 & 2) for all directions.
        up1 = setup("/monster/bat_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/bat_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/bat_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/bat_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/bat_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/bat_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/bat_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/bat_down_2", gp.tileSize, gp.tileSize);
    }

    public void setAction() {
        // Get a random direction
        getRandomDirection(10);
    }

    public void damageReaction() {
        actionLockCounter = 0;
    }

    public void checkDrop() {
        // CAST A DIE
        int i = new Random().nextInt(100) + 1;

        // SET THE MONSTER DROP
        if (i < 50) {
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if (i >= 50 && i < 75) {
            dropItem(new OBJ_Heart(gp));
        }
        if (i >= 75 && i < 100) {
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }
}
