package term2d.core;

import java.util.concurrent.TimeUnit;

public class Clock {
    private final int fps;

    public Clock(int fps) {
        this.fps = fps;
    }

    public Clock() {
        this.fps = GlobalConstants.FPS;
    }

    public void tick() {
        try {
            TimeUnit.MICROSECONDS.sleep(1000*1000 / fps);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}