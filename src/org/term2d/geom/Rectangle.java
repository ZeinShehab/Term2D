package org.term2d.geom;

import java.util.NoSuchElementException;

import org.term2d.collision.GameObject;
import org.term2d.iterator.RectangleIterator;
import org.term2d.physics.Vec2;

public class Rectangle extends GameObject implements Shape, Iterable<Point> {
    Vec2 pos;
    double width;
    double height;

    private boolean fill;

    public Rectangle(Vec2 pos, double width, double height) {
        this.pos = pos;
        this.width  = width;
        this.height = height;
        this.fill = true;
    }

    public Rectangle(double x, double y, double width, double height) {
        this(new Vec2(x, y), width, height);
    }
 
    @Override
    public Rectangle getBounds() {
        return new Rectangle(pos, width, height);
    }

    @Override
    public boolean contains(Point p) {
        return p.x >= pos.x && p.x <= maxX() && p.y >= pos.y && p.y <= maxY();
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
        return pos.x + width;
    }

    @Override
    public double maxY() {
        return pos.y + height;
    }

    @Override
    public double minX() {
        return pos.x;
    }

    @Override
    public double minY() {
        return pos.y;
    }

    @Override
    public void transform(Vec2 vel) {
        pos.add(vel);
    }

    @Override
    public boolean isBoundary(Point p) {
        return contains(p) && (p.x == pos.x || p.y == pos.y || p.x == maxX()-1 || p.y == maxY()-1);
    }

    public boolean intersects(Rectangle rect) {
		int x = (int) Math.max(pos.x, rect.pos.x);
		int y = (int) Math.min(pos.y, rect.pos.y);

		int height = (int) Math.min(
							(rect.pos.y - y) + rect.height,
							(pos.y - y) + this.height);
		int width = (int) Math.min(
							(rect.pos.x - x) + rect.width,
							(pos.x - x) + this.width);

        return !(width < 0 || height < 0);
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
                return new Point(colIndex() + pos.x, rowIndex() + pos.y);
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
