package org.term2d.iterator;

import java.util.Iterator;

public abstract class RectangleIterator implements Iterator<org.term2d.geom.Point> {
    public abstract int colIndex();

    public abstract int rowIndex();

    public abstract org.term2d.geom.Point get();    
}