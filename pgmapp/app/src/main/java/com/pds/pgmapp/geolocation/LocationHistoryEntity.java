package com.pds.pgmapp.geolocation;

import java.util.List;

public class LocationHistoryEntity {

    private String id;
    private List<LocationEntity> locations;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LocationEntity> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationEntity> locations) {
        this.locations = locations;
    }
}
