package com.pds.pgmapp.geolocation;

import java.time.LocalDateTime;

public class LocationEntity {

    private double x;
    private double y;
    private LocalDateTime date;

    public LocationEntity(double x, double y, LocalDateTime date) {
        this.x = x;
        this.y = y;
        this.date = date;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "LocationEntity{" +
                "x=" + x +
                ", y=" + y +
                ", date=" + date +
                '}';
    }
}
