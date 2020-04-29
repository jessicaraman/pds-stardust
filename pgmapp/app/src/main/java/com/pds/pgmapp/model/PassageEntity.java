package com.pds.pgmapp.model;

import java.util.Date;

public class PassageEntity {
    private int idClient;
    private String idBeacon;
    private double distance;
    private Date date;

    public PassageEntity(int idClient, String idBeacon, Date date, double distance) {
        this.idClient = idClient;
        this.idBeacon = idBeacon;
        this.date = date;
        this.distance = distance;
    }

    public int getIdClient() {
        return this.idClient;
    }
    public String getIdBeacon() {
        return this.idBeacon;
    }
    public double getDistance() {
        return this.distance;
    }
    public Date getDate() {
        return this.date;
    }
}
