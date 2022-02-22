package org.term2d.core;

import java.util.ArrayList;

import org.term2d.display.Display;
import org.term2d.geom.Shape;

public class GameInstance {
    public final ArrayList<GameObject> gameObjects;
    public final Display display;

    private final Clock clock;
    private boolean quit    = false;
    private boolean running = false;

    public GameInstance(int width, int height) {
        gameObjects = new ArrayList<>();
        display     = new Display(width, height);
        clock       = new Clock();
    }

    private void startObjects() {
        for (GameObject gameObject : gameObjects)
            gameObject.start();
    }

    private void updateObjects() {
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Shape)
                display.draw((Shape) gameObject);
            gameObject.update();
        }
    }

    public void quit() {
        quit = true;
    }

    public void addObject(GameObject gameObject) {
        gameObject.setGameInstance(this);
        gameObjects.add(gameObject);
    }

    private void mainLoop() {
        display.clearConsole();
        display.hideCursor();
        
        startObjects();

        while (!quit) {
            display.clearDisplay();

            updateObjects();

            display.show();

            clock.tick();
        }
        display.showCursor();
    }

    public void run() {
        if (!running) {
            new Thread(() -> {
                mainLoop();
            }).start();
            running = true;
        }
    }
}
