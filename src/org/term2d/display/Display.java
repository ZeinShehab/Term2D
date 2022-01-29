package org.term2d.display;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import org.term2d.geom.Point;
import org.term2d.geom.Rectangle;
import org.term2d.geom.Shape;
import org.term2d.image.Image;
import org.term2d.iterator.ImageIterator;
import org.term2d.iterator.RectangleIterator;

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
        clearDisplay();
    }

    public int width() {
        return WIDTH;
    }

    public int height() {
        return HEIGHT;
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

    public void clearDisplay() {
        fill(Pixel.BACK);
    }

    public void set(int x, int y, Pixel p) {
        display[y * WIDTH + x] = p;
    }

    /**
     * Uses 2-row compression algorithm I stole from tsoding daily on youtube
     * @throws IOException
     */
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

    public void blit(int x, int y, Image img) throws IOException {
        ImageIterator it = img.iterator();

        while (it.hasNext()) {
            int index = it.next();
            int j = it.colIndex() + x;
            int i = it.rowIndex() + y;

            // TODO: more accurate shading
            // TODO: Optimization: slow rendering speed 
            Point p = new Point(j, i);
            if (index < 2) 
                p.fill(false);
            draw(p);
        }
    }

    public boolean outOfBounds(Shape s) {
        Rectangle bounds = s.getBounds();
        
        return bounds.minX() < 0 || bounds.maxX() > WIDTH
            || bounds.minY() < 0 || bounds.maxY() > HEIGHT;
    }

    public void draw(Shape s) {
        resetCursor();

        RectangleIterator it = s.getBounds().iterator();

        while (it.hasNext()) {
            Point p = it.next();

            if (!outOfBounds(p) && s.contains(p)) {
                if (s.fill() || (s.isBoundary(p) && !(s instanceof Point)))
                    set((int) p.getX(), (int) p.getY(), Pixel.FORE);
            }                
        }
    }

    public void showBoundaries(boolean show) {
        this.showBoundaries = show;
    }
}