package com.pds.pgmapp.model;

/**
 * Adjacent Node entity
 */
public class AdjacentNode {

    private int mainNodeId;
    private int adjacentNodeId;
    private double distance;

    public AdjacentNode(int mainNodeId, int adjacentNodeId, double distance) {
        this.mainNodeId = mainNodeId;
        this.adjacentNodeId = adjacentNodeId;
        this.distance = distance;
    }

    public AdjacentNode() {
    }

    public int getMainNodeId() {
        return mainNodeId;
    }

    public void setMainNodeId(int mainNodeId) {
        this.mainNodeId = mainNodeId;
    }

    public int getAdjacentNodeId() {
        return adjacentNodeId;
    }

    public void setAdjacentNodeId(int adjacentNodeId) {
        this.adjacentNodeId = adjacentNodeId;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
