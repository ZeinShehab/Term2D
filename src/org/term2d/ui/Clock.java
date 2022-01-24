package org.term2d.ui;

import java.util.concurrent.TimeUnit;

public class Clock {
    private final int FPS;

    public Clock(int fps) {
        this.FPS = fps;
    }

    public Clock() {
        this.FPS = 30;
    }

    public void tick() throws InterruptedException {
        TimeUnit.MICROSECONDS.sleep(1000*1000 / FPS);
    }
}