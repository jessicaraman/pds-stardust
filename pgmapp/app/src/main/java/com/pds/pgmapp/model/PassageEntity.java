package com.pds.pgmapp.model;

import java.util.Date;

public class PassageEntity {
    private int idclient;
    private String idbeacon;
    private Date date;

    public PassageEntity(int idClient, String idBeacon, Date date) {
        this.idclient = idClient;
        this.idbeacon = idBeacon;
        this.date = date;
    }

    public int getIdClient() {
        return this.idclient;
    }

    public String getIdBeacon() {
        return this.idbeacon;
    }

    public Date getDate() {
        return this.date;
    }
}
