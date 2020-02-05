package com.pds.pgmapp.model;

import java.util.ArrayList;

public class Node {
    private int id;
    private String label;
    private NodeCategory nodeCategory;
    private ArrayList<Door> doors ;

    public Node(int id, String label, NodeCategory nodeCategory, ArrayList<Door> doors) {
        this.id = id;
        this.label = label;
        this.nodeCategory = nodeCategory;
        this.doors = doors;
    }

    public Node() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public NodeCategory getNodeCategory() {
        return nodeCategory;
    }

    public void setNodeCategory(NodeCategory nodeCategory) {
        this.nodeCategory = nodeCategory;
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }

    public void setDoor(ArrayList<Door> doors) {
        this.doors = doors;
    }
}
