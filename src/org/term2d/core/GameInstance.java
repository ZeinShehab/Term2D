package org.term2d.core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    public List<GameObject> findGameObjectsByType(Class<? extends GameObject> cls) {
        List<GameObject> list = gameObjects
            .stream()
            .filter(c -> c.getClass() == cls)
            .collect(Collectors.toList());
            
        return list;
    }

    public List<GameObject> findGameObjectsByTag(String tag) {
        List<GameObject> list = gameObjects
            .stream()
            .filter(s -> s.tag.equals(tag))
            .collect(Collectors.toList());

        return list;
    }

    public GameObject findByTag(String tag) {
        for (GameObject obj : gameObjects)
            if (obj.tag.equals(tag))
                return obj;
        
        return null;
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
