package com.pds.pgmapp.model;

import com.pds.pgmapp.handlers.DBHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *
 */
public class Path {
    private int id;
    private DBHandler dbHandler;
    private ArrayList<Node> nodes;
    private int datetime;

    public Path(int id, ArrayList<Node> nodes, int datetime) {
        this.id = id;
        this.nodes = nodes;
        this.datetime = datetime;
    }

    public Path(JSONObject raw, DBHandler dbh) {
        try {
            this.dbHandler = dbh;
            this.id = (int) raw.get("id");
            this.datetime = (int) raw.get("datetime");
            JSONArray arr = raw.getJSONArray("nodes");
            for (int i = 0; i < arr.length(); i++) {
                Node n = dbHandler.getNodeById((int) arr.getJSONObject(i).get("node"));
                this.nodes.add(n);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public int getDatetime() {
        return datetime;
    }

    public void setDatetime(int datetime) {
        this.datetime = datetime;
    }
}
