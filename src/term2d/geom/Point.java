package term2d.geom;

import term2d.core.Vec2;

public class Point extends Shape {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public Rectangle getBounds() {
        return new Square(x, y, 1);
    }

    @Override
    public boolean contains(Point p) {
        return x == p.x && y == p.y;
    }

    public void transform(Vec2 vel) {
        x += vel.getX();
        y += vel.getY();
    }

    @Override
    public boolean isBoundary(Point p) {
        return p.x == x && p.y == p.y;
    }
}
