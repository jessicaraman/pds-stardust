package com.pds.pgmapp.handlers;

import com.pds.pgmapp.geolocation.LocationEntity;
import com.pds.pgmapp.model.Door;
import com.pds.pgmapp.model.Node;
import com.pds.pgmapp.model.Path;

import java.util.ArrayList;

/**
 * Guidance Handler
 */
public class GuidanceHandler {
    private LocationEntity currentLocation;
    private Door nextDoorToReach;
    private Path path;
    private ArrayList<Node> reachedNodes;

    public GuidanceHandler() {
        this.reachedNodes = new ArrayList<Node>();
    }

    public LocationEntity getCurrentLocation() {
        return this.currentLocation;
    }

    public void setCurrentLocation(LocationEntity currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Path getPath() {
        return this.path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    /**
     * Compute distance between current user location and a specified Node
     *
     * @param n node
     * @return distance
     */
    public double computeDistance(Node n) {
        return this.computeDistance(n, true);
    }

    /**
     * Compute distance
     * @param node
     * @param saveNextDoor
     * @return distance
     */
    public double computeDistance(Node node, boolean saveNextDoor) {
        double dist = -1;

        // user current location P1(x1,y1)
        double x1 = this.currentLocation.getX();
        double y1 = this.currentLocation.getY();

        for (Door d : node.getDoors()) {
            // door location P2(x2,y2)
            double x2 = d.getX();
            double y2 = d.getY();

            // dist between P1 and P2 is calculated
            double tmp = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));

            // finding which door is the closest
            if (dist == -1 || tmp < dist) {
                dist = tmp;
                if (saveNextDoor) {
                    this.nextDoorToReach = d;
                }
            }
        }
        return dist;
    }

    /**
     * Compute a direction vector from current location to next point to reach
     *
     * @return
     */
    public double[] computeDirection() {
        // Direction vector from P1(x1,y1) to P2(x2,y2) is (x2-x1, y2-y1)
        double[] directionVector = new double[2];
        directionVector[0] = nextDoorToReach.getX() - this.currentLocation.getX();
        directionVector[1] = nextDoorToReach.getY() - this.currentLocation.getY();
        return directionVector;
    }

    /**
     * Find the closest node that the user has not visited yet
     *
     * @return closest Node
     */
    public Node findClosestNode() {
        double dist = -1;
        Node closestNode = null;

        // Looping on all path's nodes
        for (Node n : this.path.getNodes()) {
            // Finding the closest && a non visited one
            if ((dist == -1 || this.computeDistance(n) < dist) && this.reachedNodes.indexOf(n) == -1) {
                dist = this.computeDistance(n);
                closestNode = n;
            }
        }
        return closestNode;
    }

    public void addReachedNode(Node n) {
        this.reachedNodes.add(n);
    }
}
