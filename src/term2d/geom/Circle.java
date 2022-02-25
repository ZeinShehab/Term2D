package term2d.geom;

import term2d.core.Vec2;

public class Circle extends Shape {
    public Vec2 center;
    public final double radius;

    public Circle(Vec2 center, double radius) {
        this.center = center;
        this.radius = radius;
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
    public boolean isBoundary(Point p) {
        double distance = center.distanceSq(new Vec2(p.x+0.5, p.y+0.5));
        return distance <= radius*radius && distance > radius*radius - radius*2;
    }
}
