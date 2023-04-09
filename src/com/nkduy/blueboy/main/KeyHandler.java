package com.nkduy.blueboy.main;

import com.nkduy.blueboy.Main;
import com.nkduy.blueboy.util.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed, spacePressed;
    // DEBUG
    public boolean debugOn = false;
    public boolean godModeOn = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // TITLE STATE
        if (gp.gameState == GameState.TITLE) {
            titleState(code);
        }
        // PLAY STATE
        else if (gp.gameState == GameState.PLAY) {
            playState(code);
        }
        // PAUSE STATE
        else if (gp.gameState == GameState.PAUSE) {
            pauseState(code);
        }
        // DIALOGUE STATE
        else if (gp.gameState == GameState.DIALOGUE || gp.gameState == GameState.CUT_SCENE) {
            dialogueState(code);
        }
        // CHARACTER STATE
        else if (gp.gameState == GameState.CHARACTER) {
            characterState(code);
        }
        // OPTIONS STATE
        else if (gp.gameState == GameState.OPTIONS) {
            optionsState(code);
        }
        // GAME OVER STATE
        else if (gp.gameState == GameState.GAME_OVER) {
            gameOverState(code);
        }
        // TRADE STATE
        else if (gp.gameState == GameState.TRADE) {
            tradeState(code);
        }
        // MAP STATE
        else if (gp.gameState == GameState.MAP) {
            mapState(code);
        }
    }

    /**
     * TITLE STATE
     */
    public void titleState(int code) {
        if (gp.ui.titleScreenState == 0) {
            if (code == KeyEvent.VK_W) {
                if (gp.ui.commandNum > 0) {
                    gp.ui.commandNum--;
                }
            }
            if (code == KeyEvent.VK_S) {
                if (gp.ui.commandNum < 2) {
                    gp.ui.commandNum++;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
//                    gp.ui.titleScreenState = 1;
                    gp.gameState = GameState.PLAY;
                    gp.playMusic(0);
                    Main.gameInfo.setWindowWithSubtitle("Play");
                }
                if (gp.ui.commandNum == 1) {
                    gp.saveLoad.load();
                    gp.gameState = GameState.PLAY;
                    gp.playMusic(0);
                }
                if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        } else if (gp.ui.titleScreenState == 1) {
            if (code == KeyEvent.VK_W) {
                if (gp.ui.commandNum > 0) {
                    gp.ui.commandNum--;
                }
            }
            if (code == KeyEvent.VK_S) {
                if (gp.ui.commandNum < 3) {
                    gp.ui.commandNum++;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.player.classPlayer = "boy";
                    gp.gameState = GameState.PLAY;
                    gp.playMusic(0);
                }
                if (gp.ui.commandNum == 1 || gp.ui.commandNum == 2) {
                    System.out.println("Soon!!!");
//                    gp.gameState = gp.playState;
//                    gp.playMusic(0);
                }
                if (gp.ui.commandNum == 3) {
                    gp.ui.titleScreenState = 0;
                    gp.ui.commandNum = 0;
                }
            }
        }
    }

    /**
     * PLAY STATE
     */
    public void playState(int code) {
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_P) {
            gp.gameState = GameState.PAUSE;
            Main.gameInfo.setWindowWithSubtitle("Pause");
        }
        if (code == KeyEvent.VK_C) {
            gp.gameState = GameState.CHARACTER;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_F) {
            shotKeyPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = GameState.OPTIONS;
        }
        if (code == KeyEvent.VK_M) {
            gp.gameState = GameState.MAP;
        }
        if (code == KeyEvent.VK_N) {
            gp.map.miniMapOn = !gp.map.miniMapOn;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }

        // DEBUG
        if (code == KeyEvent.VK_T) {
            debugOn = !debugOn;
        }
//        if (code == KeyEvent.VK_R) {
//            switch (gp.currentMap) {
//                case 0: gp.tileM.loadMap("/maps/worldV3.txt", 0); break;
//                case 1: gp.tileM.loadMap("/maps/interior01.txt", 1); break;
//                case 2: gp.tileM.loadMap("/maps/dungeon01.txt", 2); break;
//                case 3: gp.tileM.loadMap("/maps/dungeon02.txt", 3); break;
//            }
//        }
        if (code == KeyEvent.VK_G) {
            godModeOn = !godModeOn;
        }
    }

    /**
     * PAUSE STATE
     */
    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gp.gameState = GameState.PLAY;
            Main.gameInfo.setWindowWithSubtitle("Play");
        }
    }

    /**
     * DIALOGUE STATE
     */
    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }

    /**
     * CHARACTER STATE
     */
    public void characterState(int code) {
        if (code == KeyEvent.VK_C || code == KeyEvent.VK_ESCAPE) {
            gp.gameState = GameState.PLAY;
        }
        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
        playerInventory(code);
    }

    /**
     * OPTIONS STATE
     */
    public void optionsState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = GameState.PLAY;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch (gp.ui.subState) {
            case 0 -> maxCommandNum = 6;
//            case 2 -> maxCommandNum = 1;
            case 3 -> maxCommandNum = 1;
        }
        if (code == KeyEvent.VK_W) {
            if (gp.ui.commandNum > 0) {
                gp.ui.commandNum--;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.commandNum < maxCommandNum) {
                gp.ui.commandNum++;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if (gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;
                    gp.playSE(9);
                }
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;
                    gp.playSE(9);
                }
            }
        }
    }

    /**
     * GAME OVER STATE
     */
    public void gameOverState(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.commandNum > 0) {
                gp.ui.commandNum--;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.commandNum < 1) {
                gp.ui.commandNum++;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = GameState.PLAY;
                gp.resetGame(false);
                gp.playMusic(0);
            } else if (gp.ui.commandNum == 1) {
                gp.gameState = GameState.TITLE;
                gp.resetGame(true);
                gp.stopMusic();
            }
        }
    }

    /**
     * TRADE STATE
     */
    public void tradeState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        if (gp.ui.subState == 0) {
            if (code == KeyEvent.VK_W) {
                if (gp.ui.commandNum > 0) {
                    gp.ui.commandNum--;
                    gp.playSE(9);
                }
            }
            if (code == KeyEvent.VK_S) {
                if (gp.ui.commandNum < 2) {
                    gp.ui.commandNum++;
                    gp.playSE(9);
                }
            }
        }

        if (gp.ui.subState == 1) {
            npcInventory(code);

            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }

        if (gp.ui.subState == 2) {
            playerInventory(code);

            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
    }

    /**
     * MAP OVER STATE
     */
    public void mapState(int code) {
        if (code == KeyEvent.VK_M) {
            gp.gameState = GameState.PLAY;
        }
    }

    public void playerInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.playerSlotCol != 0) {
                gp.ui.playerSlotCol--;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.playerSlotRow != 3) {
                gp.ui.playerSlotRow++;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.playerSlotCol != 4) {
                gp.ui.playerSlotCol++;
                gp.playSE(9);
            }
        }
    }

    public void npcInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.npcSlotRow != 0) {
                gp.ui.npcSlotRow--;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.npcSlotCol != 0) {
                gp.ui.npcSlotCol--;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.npcSlotRow != 3) {
                gp.ui.npcSlotRow++;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.npcSlotCol != 4) {
                gp.ui.npcSlotCol++;
                gp.playSE(9);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_F) {
            shotKeyPressed = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }
}
