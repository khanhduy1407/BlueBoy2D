package com.nkduy.blueboy.util;

public enum GameState {

    TITLE,
    PLAY,
    PAUSE,
    DIALOGUE,
    CHARACTER,
    OPTIONS,
    GAME_OVER,
    TRANSITION,
    TRADE,
    SLEEP,
    MAP,
    CUT_SCENE;

    public static GameState gameState = TITLE;
}
