package org.term2d.geom;

import org.term2d.core.GameObject;
import org.term2d.core.GlobalConstants;

public class Line extends GameObject implements Shape {
    public Point p1;
    public Point p2;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public Rectangle getBounds() {
        double x1 = Math.min(p1.x, p2.x);
        double x2 = Math.max(p1.x, p2.x);
        double y1 = Math.min(p1.y, p2.y);
        double y2 = Math.max(p1.y, p2.y);

        double dx = x2 - x1;
        double dy = y2 - y1;

        if (dx == 0) dx++;
        if (dy == 0) dy++;

        return new Rectangle(x1, y1, dx, dy);
    }

    protected boolean contains(Point point, double lineError) {
        Point leftPoint;
        Point rightPoint;

        // Normalize start/end to left right to make the offset calc simpler.
        if (p1.x <= p2.x) {
            leftPoint  = p1;
            rightPoint = p2;
        }
        else {
            leftPoint  = p2;
            rightPoint = p1;
        }

        // If point is out of bounds, no need to do further checks.                  
        if (point.x + lineError < leftPoint.x || rightPoint.x < point.x - lineError)
            return false;
        else if (point.y + lineError < Math.min(leftPoint.y, rightPoint.y) || 
                Math.max(leftPoint.y, rightPoint.y) < point.y - lineError)
            return false;

        double deltaX = rightPoint.x - leftPoint.x;
        double deltaY = rightPoint.y - leftPoint.y;

        // If the line is straight, the earlier boundary check is enough to determine that the point is on the line.
        // Also prevents division by zero exceptions.
        if (deltaX == 0 || deltaY == 0) 
            return true;

        double slope        = deltaY / deltaX;
        double offset       = leftPoint.y - leftPoint.x * slope;
        double calculatedY  = point.x * slope + offset;

        // Check calculated Y matches the points Y coord with some easing.
        boolean lineContains = point.y - lineError <= calculatedY && calculatedY <= point.y + lineError;

        return lineContains;  
    }

    @Override
    public boolean contains(Point p) {
        return contains(p, GlobalConstants.LINE_ERROR);
    }

    @Override
    public boolean isBoundary(Point p) {
        return contains(p);
    }

    @Override
    public void setFill(boolean fill) {

    }

    @Override
    public boolean fill() {
        return true;
    }
}
