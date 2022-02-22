package org.term2d.geom;

import org.term2d.core.Vec2;

public class Square extends Rectangle {
    public Square(double x, double y, double size) {
        super(x, y, size, size);
    }

    public Square(Vec2 pos, double size) {
        super(pos, size, size);
    }
}
