package org.term2d;

import java.util.concurrent.TimeUnit;

public class Clock {
    public final int FPS;

    public Clock(int fps) {
        this.FPS = fps;
    }

    public Clock() {
        this.FPS = 60;
    }

    public void tick() {
        try {
            TimeUnit.MICROSECONDS.sleep(1000*1000 / FPS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}