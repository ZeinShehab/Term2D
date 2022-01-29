package org.term2d.iterator;

import java.util.Iterator;

public abstract class ImageIterator implements Iterator<Integer> {
    public abstract int get();

    public abstract void set(int rgb);

    public abstract int rowIndex();

    public abstract int colIndex();
}
