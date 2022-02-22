package org.term2d.core;

import org.term2d.geom.Point;

public class Vec2 {
    public double x;
    public double y;

    public static final Vec2 up    = new Vec2(0, -1);
    public static final Vec2 down  = new Vec2(0, 1);
    public static final Vec2 left  = new Vec2(-1, 0);
    public static final Vec2 right = new Vec2(1, 0); 

    public Vec2() {
        this(0, 0);
    }

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2(double a) {
        x = a;
        y = a;
    }

    public Vec2(Point p) {
        x = p.getX();
        y = p.getY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double[] toArray() {
        return new double[] { x, y };
    }

    public boolean isZero() {
        return (x == 0 && y == 0);
    }

    public double norm() {
        return Math.sqrt(x * x + y * y);
    }

    public double normSq() {
        return x * x + y * y;
    }

    public double norm1() {
        return Math.abs(x) + Math.abs(y);
    }

    public double normInf() {
        return Math.max(Math.abs(x), Math.abs(y));
    }

    public void add(Vec2 v) {
        this.x += v.x;
        this.y += v.y;
    }

    public void add(double a) {
        x += a;
        y += a;
    }

    public Vec2 sum(Vec2 v) {
        return new Vec2(x + v.x, y + v.y);
    }

    public Vec2 sum(double a) {
        return new Vec2(x + a, y + a);
    }

    public void sub(Vec2 v) {
        this.x -= v.x;
        this.y -= v.y;
    }

    public void sub(double a) {
        x -= a;
        y -= a;
    }

    public Vec2 diff(Vec2 v) {
        return new Vec2(x - v.x, y - v.y);
    }

    public Vec2 diff(double a) {
        return new Vec2(x - a, y - a);
    }

    public void mul(double a) {
        x *= a;
        y *= a;
    }

    public Vec2 product(double a) {
        return new Vec2(x * a, y * a);
    }

    public double dot(Vec2 v) {
        return x * v.x + y * v.y;
    }

    public void normalize() {
        if (isZero())
            fail("Cannot normalize zero vector");

        mul(1.0 / norm());
    }

    public Vec2 normalized() {
        if (isZero())
            fail("Cannot normalize zero vector");

        return product(1.0 / norm());
    }

    public double angle(Vec2 v) {
        return Math.acos(dot(v) / norm() * v.norm());
    }

    public double distance(Vec2 v) {
        double dx = v.getX() - x;
        double dy = v.getY() - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double distance(Point p) {
        return distance(new Vec2(p));
    }

    public double distance1(Vec2 v) {
        double dx = Math.abs(v.getX() - x);
        double dy = Math.abs(v.getY() - y);
        return dx + dy;
    }

    public double distanceSq(Vec2 v) {
        double dx = v.getX() - x;
        double dy = v.getY() - y;
        return dx * dx + dy * dy;
    }

    public double distanceInf(Vec2 v) {
        double dx = Math.abs(v.getX() - x);
        double dy = Math.abs(v.getY() - y);
        return Math.max(dx, dy);
    }

    public void negate() {
        x = -x;
        y = -y;
    }

    public Vec2 negated() {
        return new Vec2(-x, -y);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    public boolean equals(Vec2 v, double tolerance) {
        return (
            Math.abs(x - v.x) <= tolerance && 
            Math.abs(y - v.y) <= tolerance
            );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj != null && obj instanceof Vec2) {
            return equals((Vec2) obj, GlobalConstants.EPS);
        }
        return false;
    }

    /**
     * returns clone of instance
     */
    @Override
    public Vec2 clone() {
        return new Vec2(x, y);
    }

    private void fail(String message) {
        throw new IllegalArgumentException(message);
    }
}
