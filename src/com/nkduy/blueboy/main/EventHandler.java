package com.nkduy.blueboy.main;

import com.nkduy.blueboy.data.Progress;
import com.nkduy.blueboy.entity.Entity;
import com.nkduy.blueboy.util.Area;
import com.nkduy.blueboy.util.GameState;

public class EventHandler {

    GamePanel gp;
    EventRect[][][] eventRect;
    Entity eventMaster;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventMaster = new Entity(gp);
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;

        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;

                if (row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }

        setDialogue();
    }

    public void setDialogue() {
        eventMaster.dialogues[0][0] = "You fall into a pit!";

        eventMaster.dialogues[1][0] = """
                You drink the water.
                Your life and mana have been recovered.
                (The progress has been saved)""";
        eventMaster.dialogues[1][1] = "Damn, this is good water.";
    }

    public void checkEvent() {
        // Check if the player character is more than 1 tile away from the last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            if (hit(0, 25, 6, "any")) { marker( 1, "Adventure Map", 23, 21, Area.OUTSIDE); }
            else if (hit(1, 27, 16, "right")) { damagePit(GameState.DIALOGUE); }
            else if (hit(1, 23, 12, "up")) { healingPool(GameState.DIALOGUE); }
            else if (hit(1, 10, 39, "any")) { teleport(2, 12, 13, Area.INDOOR); } // to merchant's house
            else if (hit(2, 12, 13, "any")) { teleport(1, 10, 39, Area.OUTSIDE); } // to outside
            else if (hit(2, 12, 9, "up")) { speak(gp.npc[1][0]); }
            else if (hit(1, 12, 9, "any")) { teleport(3, 9, 41, Area.DUNGEON); } // to the dungeon
            else if (hit(3, 9, 41, "any")) { teleport(1, 12, 9, Area.OUTSIDE); } // to outside
            else if (hit(3, 8, 7, "any")) { teleport(4, 26, 41, Area.DUNGEON); } // to B2
            else if (hit(4, 26, 41, "any")) { teleport(3, 8, 7, Area.DUNGEON); } // to B1
            else if (hit(4, 25, 27, "any")) { skeletonLord(); } // BOSS
        }
    }

    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;

        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }

        return hit;
    }

    public void damagePit(GameState gameState) {
        gp.gameState = gameState;
        eventMaster.startDialogue(eventMaster, 0);
        gp.player.life -= 1;
        canTouchEvent = false;
    }

    public void healingPool(GameState gameState) {
        if (gp.keyH.enterPressed) {
            gp.gameState = gameState;
            gp.player.attackCancel = true;
            eventMaster.startDialogue(eventMaster, 1);
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
        }
    }

    public void teleport(int map, int col, int row, Area area) {
        gp.gameState = GameState.TRANSITION;
        gp.nextArea = area;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
        gp.playSE(13);
    }

    public void marker(int map, String dest, int col, int row, Area area) {
        gp.gameState = GameState.DIALOGUE;
        eventMaster.dialogues[2][0] = "Go to '" + dest + "'.";
        eventMaster.startDialogue(eventMaster, 2);
//        canTouchEvent = false;

        if (gp.keyH.enterPressed) {
            gp.player.attackCancel = true;
            teleport(map, col, row, area);
        }
    }

    public void speak(Entity entity) {
        if (gp.keyH.enterPressed) {
            gp.gameState = GameState.DIALOGUE;
            gp.player.attackCancel = true;
            entity.speak();
        }
    }

    public void skeletonLord() {
        if (!gp.bossBattleOn && !Progress.skeletonLordDefeated) {
            gp.gameState = GameState.CUT_SCENE;
            gp.csManager.sceneNum = gp.csManager.skeletonLord;
        }
    }
}
