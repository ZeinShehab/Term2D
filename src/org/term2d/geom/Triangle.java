package org.term2d.geom;

import org.term2d.game.GameObject;

public class Triangle extends GameObject implements Shape {
    private Point p1;
    private Point p2;
    private Point p3;

    private boolean fill;

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        fill = true;
    }

    @Override
    public Rectangle getBounds() {
        double x1 = Math.min(p1.x, Math.min(p2.x, p3.x));
        double x2 = Math.max(p1.x, Math.max(p2.x, p3.x));
        double y1 = Math.min(p1.y, Math.min(p2.y, p3.y));
        double y2 = Math.max(p1.y, Math.max(p2.y, p3.y));
        return new Rectangle(x1, y1, x2 - x1, y2 - y1);
    }

    private double sign(Point p1, Point p2, Point p3) {
        return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y);
    }

    @Override
    public boolean contains(Point p) {
        double d1, d2, d3;
        boolean has_neg, has_pos;

        d1 = sign(p, p1, p2);
        d2 = sign(p, p2, p3);
        d3 = sign(p, p3, p1);

        has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);
        
        return !(has_neg && has_pos);
    }

    @Override
    public boolean isBoundary(Point p) {
        Line l1 = new Line(p1, p2);
        Line l2 = new Line(p2, p3);
        Line l3 = new Line(p3, p1);
        return l1.contains(p, 1) || l2.contains(p, 1) || l3.contains(p, 1);
    }

    @Override
    public void setFill(boolean fill) {
        this.fill = fill;        
    }

    @Override
    public boolean fill() {
        return fill;
    }
}
