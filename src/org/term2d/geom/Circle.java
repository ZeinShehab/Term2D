package org.term2d.geom;

import org.term2d.game.GameObject;
import org.term2d.physics.Vec2;

public class Circle extends GameObject implements Shape {
    public Vec2   center;
    public double radius;

    private boolean fill;

    public Circle(Vec2 center, double radius) {
        this.center = center;
        this.radius = radius;
        this.fill = true;
    }

    @Override
    public double minX() {
        return center.x - radius;
    }

    @Override
    public double minY() {
        return center.y - radius;
    }

    @Override
    public Rectangle getBounds() {
        return new Square(center.diff(radius), radius+radius);
    }

    @Override
    public boolean contains(Point p) {
        return center.distanceSq(new Vec2(p.x+0.5, p.y+0.5)) <= radius*radius;
    } 

    @Override
    public double height() {
        return radius + radius;
    }

    @Override
    public double width() {
        return radius + radius;
    }

    @Override
    public double maxX() {
        return center.x + radius;
    }

    @Override
    public double maxY() {
        return center.y + radius;
    }

    @Override
    public void transform(Vec2 vel) {
        center.add(vel);
    } 

    @Override
    public boolean isBoundary(Point p) {
        double distance = center.distanceSq(new Vec2(p.x+0.5, p.y+0.5));
        return distance <= radius*radius && distance > radius*radius - radius*2;
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
