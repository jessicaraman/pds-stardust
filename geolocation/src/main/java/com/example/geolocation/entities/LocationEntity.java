package com.example.geolocation.entities;

import java.time.LocalDateTime;

public class LocationEntity {

    private double x;
    private double y;
    private LocalDateTime date;

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
}
