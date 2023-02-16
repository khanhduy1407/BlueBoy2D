package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Blood extends Entity {

    public static final String objName = "Blood Sword";

    public OBJ_Sword_Blood(GamePanel gp) {
        super(gp);

        type = type_sword;
        sword_type = "Blood";
        name = objName;
        down1 = setup("/objects/sword_blood", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nA blood sword.";
        price = 50;
        knockBackPower = 2;
        motion1_duration = 5;
        motion2_duration = 25;
    }
}
