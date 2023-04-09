package com.nkduy.blueboy.main;

import com.nkduy.blueboy.entity.Entity;
import com.nkduy.blueboy.object.*;

public class EntityGenerator {

    GamePanel gp;

    public EntityGenerator(GamePanel gp) {
        this.gp = gp;
    }

    public Entity getObject(String itemName) {
        Entity obj = null;

        switch (itemName) {
            case OBJ_Axe.objName -> obj = new OBJ_Axe(gp);
            case OBJ_Boots.objName -> obj = new OBJ_Boots(gp);
            case OBJ_Chest.objName -> obj = new OBJ_Chest(gp);
            case OBJ_Coin_Bronze.objName -> obj = new OBJ_Coin_Bronze(gp);
            case OBJ_Door.objName -> obj = new OBJ_Door(gp);
            case OBJ_Door_Iron.objName -> obj = new OBJ_Door_Iron(gp);
            case OBJ_Fireball.objName -> obj = new OBJ_Fireball(gp);
            case OBJ_Heart.objName -> obj = new OBJ_Heart(gp);
            case OBJ_Key.objName -> obj = new OBJ_Key(gp);
            case OBJ_Lantern.objName -> obj = new OBJ_Lantern(gp);
            case OBJ_ManaCrystal.objName -> obj = new OBJ_ManaCrystal(gp);
            case OBJ_Marker.objName -> obj = new OBJ_Marker(gp);
            case OBJ_Pickaxe.objName -> obj = new OBJ_Pickaxe(gp);
            case OBJ_Potion_Red.objName -> obj = new OBJ_Potion_Red(gp);
            case OBJ_Rock.objName -> obj = new OBJ_Rock(gp);
            case OBJ_Shield_Blue.objName -> obj = new OBJ_Shield_Blue(gp);
            case OBJ_Shield_Wood.objName -> obj = new OBJ_Shield_Wood(gp);
            case OBJ_Sword_Blood.objName -> obj = new OBJ_Sword_Blood(gp);
            case OBJ_Sword_Normal.objName -> obj = new OBJ_Sword_Normal(gp);
            case OBJ_Tent.objName -> obj = new OBJ_Tent(gp);
        }

        return obj;
    }
}
