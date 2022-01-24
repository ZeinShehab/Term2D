package org.ui;

import org.geom.Point;
import org.geom.Rectangle;
import org.geom.Shape;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class Display {
    private final int WIDTH;
    private final int HEIGHT;    
    
    private Pixel[] display;
    private boolean showBoundaries;

    public Display(int width, int height) {
        this.WIDTH   = width;
        this.HEIGHT  = height;
        this.display = new Pixel[WIDTH * HEIGHT];
        clearConsole();
        clear();
    }

    public void hideCursor() {
        System.out.printf("\u001B[?25l");
    }

    public void showCursor() {
        System.out.printf("\u001B[?25h");
    }
    
    public void resetCursor() {
        System.out.printf("\033[%dD", WIDTH);
        System.out.printf("\033[%dA", HEIGHT/2);
    }

    public void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void fill(Pixel p) {
        Arrays.fill(display, p);
    }

    public void clear() {
        fill(Pixel.BACK);
    }

    public void set(int x, int y, Pixel p) {
        display[y * WIDTH + x] = p;
    }

    public void show() throws IOException {
        char[][] chars = {
            {' ', '_'},
            {'^', '@'}
        };

        OutputStream row = new BufferedOutputStream(System.out);

        if (showBoundaries) {
            Rectangle bounds = new Rectangle(0, 0, WIDTH, HEIGHT);
            bounds.fill(false);
            draw(bounds);
        }

        for (int y = 0; y < HEIGHT/2; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Pixel t = display[(y*2 + 0)*WIDTH + x];
                Pixel b = display[(y*2 + 1)*WIDTH + x];
                row.write(chars[t.ordinal()][b.ordinal()]);
            }
            row.write('\n');
            row.flush();
        }
    }

    public boolean outOfBounds(Shape s) {
        Rectangle bounds = s.getBounds();
        
        return bounds.minX() < 0 || bounds.maxX() > WIDTH
            || bounds.minY() < 0 || bounds.maxY() > HEIGHT;
    }

    public void draw(Shape s) {
        resetCursor();
        for (int y = (int) s.minY(); y < s.maxY(); y++) {
            for (int x = (int) s.minX(); x < s.maxX(); x++) {
                if (!outOfBounds(new Point(x, y)) && s.contains(new Point(x, y))) {
                    if (s.fill() || s.isBoundary(new Point(x, y)))
                        set(x, y, Pixel.FORE);
                }
            }
        }
    }

    public void showBoundaries(boolean show) {
        this.showBoundaries = show;
    }
}