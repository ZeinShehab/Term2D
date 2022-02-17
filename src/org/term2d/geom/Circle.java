package org.term2d.geom;

import org.term2d.game.GameObject;
import org.term2d.physics.Vec2;

public class Circle extends GameObject implements Shape {
    public Vec2 center;
    public final double radius;

    private boolean fill;

    public Circle(Vec2 center, double radius) {
        this.center = center;
        this.radius = radius;
        this.fill = true;
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
    public void transform(Vec2 vel) {
        center.add(vel);
    } 

    @Override
    public boolean isBoundary(Point p) {
        double distance = center.distanceSq(new Vec2(p.x+0.5, p.y+0.5));
        return distance <= radius*radius && distance > radius*radius - radius*2;
    }

    @Override
    public void setFill(boolean fill) {
        this.fill = fill;
    }

    @Override
    public boolean fill() {
        return fill;
    }
}
