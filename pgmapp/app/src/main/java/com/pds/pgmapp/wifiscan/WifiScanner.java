package com.pds.pgmapp.wifiscan;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class WifiScanner {

    private Context context;
    private WifiManager wifiManager;


    public WifiScanner(final Context context) {
        this.context = context;
        this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    public void startScan() {

        wifiManager.startScan();

    }

}
