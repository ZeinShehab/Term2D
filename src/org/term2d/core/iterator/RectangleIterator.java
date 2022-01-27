package org.term2d.core.iterator;

import java.util.Iterator;

public abstract class RectangleIterator implements Iterator<org.term2d.geom.Point> {
    public abstract int colIndex();

    public abstract int rowIndex();

    public abstract org.term2d.geom.Point get();    
}