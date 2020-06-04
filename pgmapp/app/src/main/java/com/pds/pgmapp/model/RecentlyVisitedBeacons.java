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

    public boolean addBeacon(String idBeacon, Date currentDate) {
        if (!isThisBeaconPresent(idBeacon)) {
            this.put(idBeacon, currentDate);
            return true;
        } else {
            if (!thisBeaconHasBeenVisitedRecently(idBeacon, currentDate)) {
                if (this.get(idBeacon) != null) {
                    this.remove(idBeacon);
                }
                this.put(idBeacon, currentDate);
                return true;
            }
        }
        return false;
    }

    public Date getDateOfBeacon(String idBeacon) {
        if(this.get(idBeacon) != null) {
           return this.get(idBeacon);
        }
        return null;
    }
}
