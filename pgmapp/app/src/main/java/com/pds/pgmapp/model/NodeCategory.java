package com.pds.pgmapp.model;

/**
 * Node Category entity
 */
public class NodeCategory {
    private int id;
    private String label;

    public NodeCategory(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public NodeCategory () {}

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
}
