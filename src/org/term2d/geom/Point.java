package org.term2d.geom;

import org.term2d.physics.Vec2;

public class Point implements Shape {
    protected double x;
    protected double y;

    private boolean fill;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.fill = true;
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

    @Override
    public double height() {
        return 1;        
    }

    @Override
    public double width() {
        return 1;
    }

    @Override
    public double maxX() {
        return x + 1;
    }

    @Override
    public double maxY() {
        return y + 1;
    }

    @Override
    public double minX() {
        return x;
    }

    @Override
    public double minY() {
        return y;
    }

    public void transform(Vec2 vel) {
        x += vel.getX();
        y += vel.getY();
    }

    @Override
    public boolean isBoundary(Point p) {
        return p.x == x && p.y == p.y;
    }

    @Override
    public void fill(boolean fill) {
        this.fill = fill;        
    }

    @Override
    public boolean fill() {
        return fill;
    }
}
