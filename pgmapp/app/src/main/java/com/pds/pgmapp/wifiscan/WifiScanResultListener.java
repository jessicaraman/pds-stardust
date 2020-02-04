package com.pds.pgmapp.wifiscan;

import android.net.wifi.ScanResult;

import java.util.List;

public interface WifiScanResultListener {

    void onWifiScanReceive(List<ScanResult> scanResults);

}
