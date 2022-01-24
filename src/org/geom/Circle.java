package org.geom;

import org.physics.Vec2;

public class Circle implements Shape {
    private Vec2 c;
    private double r;
    private double x;
    private double y;
    private boolean fill;

    public Circle(Vec2 c, double r) {
        this.c = c;
        this.r = r;
        this.fill = true;

        Vec2 v = c.diff(Math.floor(r));
        x = v.getX();
        y = v.getY();
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
    public Rectangle getBounds() {
        return new Square(x, y, r+r);
    }

    @Override
    public boolean contains(Point p) {
        return c.distanceSq(new Vec2(p.x+0.5, p.y+0.5)) <= r*r;
    } 

    @Override
    public double height() {
        return r + r;
    }

    @Override
    public double width() {
        return r + r;
    }

    @Override
    public double maxX() {
        return x + r + r;
    }

    @Override
    public double maxY() {
        return y + r + r;
    }

    @Override
    public void transform(Vec2 vel) {
        c.add(vel);
        x += vel.getX();
        y += vel.getY();
    }

    @Override
    public boolean isBoundary(Point p) {
        double distance = c.distanceSq(new Vec2(p.x+0.5, p.y+0.5));
        return distance <= r*r && distance > r*r - r*2;
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
