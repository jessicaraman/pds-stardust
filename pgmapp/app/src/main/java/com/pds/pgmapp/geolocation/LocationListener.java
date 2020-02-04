package com.pds.pgmapp.geolocation;

import com.pds.pgmapp.exceptions.WifiScanException;

public interface LocationListener {

    void onPositionChange(Location location) throws WifiScanException;

}
