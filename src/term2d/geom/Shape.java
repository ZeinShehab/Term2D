package term2d.geom;

import term2d.core.GameObject;

public abstract class Shape extends GameObject {
    public boolean fill = true;
    public boolean visible = true;

    public abstract Rectangle getBounds();

    public abstract boolean contains(Point p);

    public abstract boolean isBoundary(Point p);

    @Override
    public void update() {
        if (visible)
            game.display.draw(this);
    }
}
