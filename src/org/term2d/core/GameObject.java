package org.term2d.core;


public class GameObject {
    public String tag;
    protected GameInstance game;

    public GameObject() {
        tag = "";
        game = null;
    }

    protected void setGameInstance(GameInstance gameInstance) {
        game = gameInstance;
    }

    public void start() {

    }

    public void update() {

    }
}