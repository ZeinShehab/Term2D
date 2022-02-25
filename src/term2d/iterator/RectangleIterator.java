package term2d.iterator;

import java.util.Iterator;

public abstract class RectangleIterator implements Iterator<term2d.geom.Point> {
    public abstract int colIndex();

    public abstract int rowIndex();

    public abstract term2d.geom.Point get();    
}