package com.pds.pgmapp.wifiscan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;

public class WifiScanResultReceiver extends BroadcastReceiver {

    private final Context context;
    private final WifiManager wifiManager;
    private WifiScanResultListener wifiScanResultListener;

    public WifiScanResultReceiver(final Context context, final WifiManager wifiManager, final WifiScanResultListener wifiScanResultListener) {

        this.context = context;
        this.wifiManager = wifiManager;
        this.wifiScanResultListener = wifiScanResultListener;
        context.registerReceiver(this, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

    }

    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {

            wifiScanResultListener.onWifiScanReceive(wifiManager.getScanResults());

        }

    }

}