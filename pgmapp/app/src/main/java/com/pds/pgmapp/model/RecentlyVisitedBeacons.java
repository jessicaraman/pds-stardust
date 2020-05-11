package com.pds.pgmapp.model;

import java.util.Date;
import java.util.HashMap;

public class RecentlyVisitedBeacons extends HashMap<String, Date> {

    public RecentlyVisitedBeacons() {}

    public boolean isThisBeaconPresent(String idBeacon) {
        return this.containsKey(idBeacon);
    }

    public boolean thisBeaconHasBeenVisitedRecently(String idBeacon, Date currentDate) {
        return !(this.get(idBeacon).compareTo(new Date(currentDate.getTime() - 30 * 1000)) < 0);
    }

}
