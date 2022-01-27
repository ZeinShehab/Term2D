package org.term2d.geom;

import java.util.NoSuchElementException;

import org.term2d.core.iterator.RectangleIterator;
import org.term2d.core.physics.Vec2;

public class Rectangle implements Shape, Iterable<Point> {
    private double x;
    private double y;
    private double width;
    private double height;

    private boolean fill;

    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width  = width;
        this.height = height;
        this.fill = true;
    }
 
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public boolean contains(Point p) {
        return p.getX() >= x && p.getX() <= maxX() && p.getY() >= y && p.getY() <= maxY();
    }

    @Override
    public double height() {
        return height;
    }

    @Override
    public double width() {
        return width;
    }

    @Override
    public double maxX() {
        return x + width;
    }

    @Override
    public double maxY() {
        return y + height;
    }

    @Override
    public double minX() {
        return x;
    }

    @Override
    public double minY() {
        return y;
    }

    @Override
    public void transform(Vec2 vel) {
        x += vel.getX();
        y += vel.getY();
    }

    @Override
    public boolean isBoundary(Point p) {
        return contains(p) && (p.x == x || p.y == y || p.x == maxX()-1 || p.y == maxY()-1);
    }

    @Override
    public void fill(boolean fill) {
        this.fill = fill;        
    }

    @Override
    public boolean fill() {
        return fill;
    }

    @Override
    public RectangleIterator iterator() {
        return new RectangleIterator() {
            private long limit = (long) (width * height);
            private int index = -1;

            @Override
            public int colIndex() {
                return (int) (index - rowIndex() * width);
            }

            @Override
            public int rowIndex() {
                return (int) (index / width);
            }

            @Override
            public Point get() {
                return new Point(colIndex() + x, rowIndex() + y);
            }

            @Override
            public boolean hasNext() {
                return index + 1 < limit;
            }

            @Override
            public Point next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                index++;
                return get();
            }
        };
    }
}