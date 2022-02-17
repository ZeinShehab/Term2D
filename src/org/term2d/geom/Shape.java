package org.term2d.geom;

public interface Shape {
    public Rectangle getBounds();

    public boolean contains(Point p);

    public void transform(org.term2d.physics.Vec2 vel); // TODO: neccessary?

    public boolean isBoundary(Point p);

    public void setFill(boolean fill);                     // TODO: better fill checking

    public boolean fill();
}
