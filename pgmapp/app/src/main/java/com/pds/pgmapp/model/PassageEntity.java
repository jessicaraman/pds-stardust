package com.pds.pgmapp.model;

import java.util.Date;

public class PassageEntity {
    private int idclient;
    private String idbeacon;
    private double distance;
    private Date date;

    public PassageEntity(int idClient, String idBeacon, Date date, double distance) {
        this.idclient = idClient;
        this.idbeacon = idBeacon;
        this.date = date;
        this.distance = distance;
    }

    public int getIdClient() {
        return this.idclient;
    }

    public String getIdBeacon() {
        return this.idbeacon;
    }

    public double getDistance() {
        return this.distance;
    }

    public Date getDate() {
        return this.date;
    }
}
