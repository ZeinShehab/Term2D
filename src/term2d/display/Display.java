package term2d.display;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import term2d.geom.Line;
import term2d.geom.Point;
import term2d.geom.Shape;
import term2d.image.Image;
import term2d.iterator.ImageIterator;
import term2d.iterator.RectangleIterator;

public class Display {
    public final int WIDTH;
    public final int HEIGHT;    
    public final Line[] BOUDARIES;
    
    private Pixel[] display;
    private boolean showBoundaries;

    public Display(int width, int height) {
        WIDTH   = width;
        HEIGHT  = height;

        Point topLeft     = new Point(0, 0);
        Point topRight    = new Point(WIDTH - 1, 0);
        Point bottomLeft  = new Point(0, HEIGHT - 1);
        Point bottomRight = new Point(WIDTH-1, HEIGHT-1);
        BOUDARIES = new Line[]{
            new Line(topLeft, topRight),
            new Line(topLeft, bottomLeft),
            new Line(bottomLeft, bottomRight),
            new Line(bottomRight, topRight)
        };

        this.display = new Pixel[WIDTH * HEIGHT];

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
    public void show() {
        char[][] chars = {
            {' ', '_'},
            {'^', '@'}
        };

        OutputStream row = new BufferedOutputStream(System.out);

        if (showBoundaries)
            for (Line line : BOUDARIES)
                draw(line);
    
        try {
            for (int y = 0; y < HEIGHT/2; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    Pixel t = display[(y*2 + 0)*WIDTH + x];
                    Pixel b = display[(y*2 + 1)*WIDTH + x];
                    row.write(chars[t.ordinal()][b.ordinal()]);
                }
                row.write('\n');
                row.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void blit(int x, int y, Image img) throws IOException {
        ImageIterator it = img.iterator();

        while (it.hasNext()) {
            Pixel p = it.next();
            int j = it.colIndex() + x;
            int i = it.rowIndex() + y;

            set(j, i, p);
        }
    }

    public boolean outOfBounds(Point p) {
        return p.x < 0 || p.x+1 > WIDTH || p.y < 0 || p.y+1 > HEIGHT;
    }

    public void draw(Shape s) {
        resetCursor();

        RectangleIterator it = s.getBounds().iterator();

        while (it.hasNext()) {
            Point p = it.next();

            if (!outOfBounds(p) && s.contains(p)) {
                if (s.fill() || s.isBoundary(p))
                    set((int) p.x, (int) p.y, Pixel.FORE);
            }                
        }
    }

    public void showBoundaries(boolean show) {
        this.showBoundaries = show;
    }
}