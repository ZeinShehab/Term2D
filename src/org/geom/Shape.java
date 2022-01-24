package org.geom;

public interface Shape {
    public Rectangle getBounds();

    public boolean contains(Point p);

    public double height();

    public double width();

    public double maxX();

    public double maxY();

    public double minX();
    
    public double minY();

    public void transform(org.physics.Vec2 vel);

    public boolean isBoundary(Point p);

    public void fill(boolean fill);

    public boolean fill();
}
