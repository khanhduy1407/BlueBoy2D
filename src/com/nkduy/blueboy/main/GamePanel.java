package com.nkduy.blueboy.main;

import com.nkduy.blueboy.Main;
import com.nkduy.blueboy.ai.PathFinder;
import com.nkduy.blueboy.data.SaveLoad;
import com.nkduy.blueboy.entity.Entity;
import com.nkduy.blueboy.player.Player;
import com.nkduy.blueboy.environment.EnvironmentManager;
import com.nkduy.blueboy.util.Area;
import com.nkduy.blueboy.util.GameState;
import com.nkduy.blueboy.tile.Map;
import com.nkduy.blueboy.tile.TileManager;
import com.nkduy.blueboy.tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

// This class inherits JPanel class
public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public int maxWorldCol;
    public int maxWorldRow;
    public final int maxMap = 10;
    public int currentMap = 0;

    // FOR FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

    // Frames-Per-Second (FPS)
    int FPS = 60;
    long numFPS;

    // SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    public Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    EnvironmentManager eManager = new EnvironmentManager(this);
    Map map = new Map(this);
    SaveLoad saveLoad = new SaveLoad(this);
    public EntityGenerator eGenerator = new EntityGenerator(this);
    public CutsceneManager csManager = new CutsceneManager(this);
    public GameState gameState;
    Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Entity[][] obj = new Entity[maxMap][20];
    public Entity[][] npc = new Entity[maxMap][10];
    public Entity[][] monster = new Entity[maxMap][20];
    public InteractiveTile[][] iTile = new InteractiveTile[maxMap][50];
    public Entity[][] projectile = new Entity[maxMap][20];
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    // OTHERS
    public boolean bossBattleOn = false;

    // AREA
    public Area currentArea;
    public Area nextArea;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        // If set to true, all the drawing from this component will be done in an offscreen painting buffer.
        // In short, enabling this can improve game's rendering performance
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        // With this, this GamePanel can be "focused" to receive key input.
        this.setFocusable(true);
    }

    // Create this method, so we can add other setup stuff in the future
    public void setupGame() {
        aSetter.setObject(); // we keep the acquired items and the destroyed objects' status etc.
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        aSetter.setMarker();

        eManager.setup();

        gameState = GameState.TITLE;
//        gameState = playState; // test
//        playMusic(0); // if test gameState = playState, enable music
//        playMusic(19); // if test gameState = playState, enable music, dungeon
//        currentArea = outside;
//        currentArea = dungeon;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if (fullScreenOn) {
            setFullScreen();
        }
    }

    public void resetGame(boolean restart) {
        stopMusic();
        currentArea = Area.OUTSIDE;
        removeTempEntity();
        bossBattleOn = false;
        player.setDefaultPositions(0, 23, 21);
        player.restoreStatus();
        player.resetCounter();
        aSetter.setNPC();
        aSetter.setMonster();

        if (restart) {
            player.setDefaultValues();
            aSetter.setObject();
            aSetter.setInteractiveTile();
            eManager.lighting.resetDay();
        }
    }

    public void setFullScreen() {
        // GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        // GET FULL SCREEN WIDTH AND HEIGHT
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        double drawInterval = (double) 1000000000/FPS; // 0.0167 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime -lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                drawToTempScreen(); // draw everything to the buffered image
                drawToScreen(); // draw the buffered image to the screen
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                numFPS = drawCount;
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (gameState == GameState.PLAY) {
            // PLAYER
            player.update();

            // NPC
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }

            // MONSTER
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].alive && !monster[currentMap][i].dying) {
                        monster[currentMap][i].update();
                    }
                    if (!monster[currentMap][i].alive) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }

            // PROJECTILE
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    if (projectile[currentMap][i].alive) {
                        projectile[currentMap][i].update();
                    } else {
                        projectile[currentMap][i] = null;
                    }
                }
            }

            // PARTICLE
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (particleList.get(i).alive) {
                        particleList.get(i).update();
                    } else {
                        particleList.remove(i);
                    }
                }
            }

            // INTERACTIVE TILE
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }

            // ENVIRONMENT
            eManager.update();
        }
    }

    public void drawToTempScreen() {
        // DEBUG
        long drawStart = 0;
        if (keyH.debugOn) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if (gameState == GameState.TITLE) {
            ui.draw(g2);
        }
        // MAP SCREEN
        else if (gameState == GameState.MAP) {
            map.drawFullMapScreen(g2);
        }
        // OTHERS
        else {
            // TILE
            tileM.draw(g2);

            // INTERACTIVE TILE
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }

            // ADD ENTITIES TO THE LIST
            entityList.add(player);

            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }

            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }

            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }

            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    entityList.add(projectile[currentMap][i]);
                }
            }

            for (Entity entity : particleList) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }

            // SORT
            entityList.sort((e1, e2) -> {
                int result = Integer.compare(e1.worldY, e2.worldY);
                return result;
            });

            // DRAW ENTITIES
            for (Entity entity : entityList) {
                entity.draw(g2);
            }

            // EMPTY ENTITY
            entityList.clear();

            // ENVIRONMENT
            eManager.draw(g2);

            // MINI MAP
            map.drawMiniMap(g2);

            // CUTSCENE
            csManager.draw(g2);

            // UI
            ui.draw(g2);
        }

        // DEBUG
        if (keyH.debugOn) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);
            int x= 10;
            int y = 360;
            int lineHeight = 20;

            g2.drawString("Username: " + Main.username, x, y); y += lineHeight;
            g2.drawString("FPS: " + numFPS, x, y); y += lineHeight;
            g2.drawString("Version: " + Main.gameInfo.version, x, y); y += lineHeight;
            g2.drawString("Class: " + player.classPlayer, x, y); y += lineHeight;
            g2.drawString("MapNum: " + currentMap, x, y); y += lineHeight;
            g2.drawString("WorldX: " + player.worldX, x, y); y += lineHeight;
            g2.drawString("WorldY: " + player.worldY, x, y); y += lineHeight;
            g2.drawString("Col: " + (player.worldX + player.solidArea.x)/tileSize, x, y); y += lineHeight;
            g2.drawString("Row: " + (player.worldY + player.solidArea.y)/tileSize, x, y); y += lineHeight;
            g2.drawString("Draw Time: " + passed, x, y); y += lineHeight;
            g2.drawString("God Mode: " + keyH.godModeOn, x, y);
        }
    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

    public void changeArea() {
        if (nextArea != currentArea) {
            stopMusic();

            if (nextArea == Area.OUTSIDE) {
                playMusic(0);
            }
            if (nextArea == Area.INDOOR) {
                playMusic(18);
            }
            if (nextArea == Area.DUNGEON) {
                playMusic(19);
            }

            aSetter.setNPC();
        }
        currentArea = nextArea;
        aSetter.setMonster();
    }

    public void removeTempEntity() {
        for (int mapNum = 0; mapNum < maxMap; mapNum++) {
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[mapNum][i] != null && obj[mapNum][i].temp) {
                    obj[mapNum][i] = null;
                }
            }
        }
    }
}
