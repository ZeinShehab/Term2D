package term2d.geom;

import java.util.NoSuchElementException;

import term2d.core.GameObject;
import term2d.core.Vec2;
import term2d.iterator.RectangleIterator;

public class Rectangle extends GameObject implements Shape, Iterable<Point> {
    public Vec2 position;
    public final double width;
    public final double height;

    private boolean fill;

    public Rectangle(Vec2 pos, double width, double height) {
        this.position = pos;
        this.width  = width;
        this.height = height;
        this.fill = true;
    }

    public Rectangle(double x, double y, double width, double height) {
        this(new Vec2(x, y), width, height);
    }
 
    @Override
    public Rectangle getBounds() {
        return new Rectangle(position, width, height);
    }

    @Override
    public boolean contains(Point p) {
        return p.x >= position.x && p.x <= maxX() && p.y >= position.y && p.y <= maxY();
    }


    public double maxX() {
        return position.x + width;
    }

    public double maxY() {
        return position.y + height;
    }

    @Override
    public boolean isBoundary(Point p) {
        return contains(p) && (p.x == position.x || p.y == position.y || p.x == maxX()-1 || p.y == maxY()-1);
    }

    public boolean intersects(Rectangle rect) {
		int x = (int) Math.max(position.x, rect.position.x);
		int y = (int) Math.min(position.y, rect.position.y);

		int height = (int) Math.min(
							(rect.position.y - y) + rect.height,
							(position.y - y) + this.height);
		int width = (int) Math.min(
							(rect.position.x - x) + rect.width,
							(position.x - x) + this.width);

        return !(width < 0 || height < 0);
	}

    @Override
    public void setFill(boolean fill) {
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
                return new Point(colIndex() + position.x, rowIndex() + position.y);
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
