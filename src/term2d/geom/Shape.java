package term2d.geom;

public interface Shape {
    public Rectangle getBounds();

    public boolean contains(Point p);

    public boolean isBoundary(Point p);

    public void setFill(boolean fill);

    public boolean fill();
}
