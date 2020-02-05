package com.pds.pgmapp.model;

/**
 * Node entity
 */
public class _Node {

    /**
     * Properties
     */
    int x, y;
    String direction;

    /**
     * Constructor
     *
     * @param x
     * @param y
     * @param direction
     */
    public _Node(int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getDirection() {
        return direction;
    }
}
