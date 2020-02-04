package com.pds.pgmapp.geolocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;

import com.pds.pgmapp.exceptions.WifiScanException;

import java.time.LocalDateTime;

public class LocationReceiver extends BroadcastReceiver {

    private final Context context;
    private LocationListener locationListener;
    public final static double DEFAULT_LOCATION_VALUE = 0.0;


    public LocationReceiver(final Context context, final LocationListener locationListener) {

        this.context = context;
        this.locationListener = locationListener;
        context.registerReceiver(this, new IntentFilter("LOCATION"));

    }

    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if ("LOCATION".equals(action)) {

            Location location = new Location(intent.getDoubleExtra("x", DEFAULT_LOCATION_VALUE), intent.getDoubleExtra("y", DEFAULT_LOCATION_VALUE));

            Log.d("LocationReceiver",location.getX() + " , " + location.getY());

            try {
                locationListener.onPositionChange(location);
            } catch (WifiScanException e) {
                e.printStackTrace();
            }

        }

    }

    public void unregister() {
        context.unregisterReceiver(this);
    }

}