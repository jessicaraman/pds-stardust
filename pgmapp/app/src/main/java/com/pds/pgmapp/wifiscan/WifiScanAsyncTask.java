package com.pds.pgmapp.wifiscan;

import android.os.AsyncTask;

public class WifiScanAsyncTask extends AsyncTask<Void, Void, Void> {

    private final WifiScanner wifiScanner;
    private final int TIME_BETWEEN_SCAN_IN_MILLIS = 1000;


    public WifiScanAsyncTask(final WifiScanner scanner) {
        this.wifiScanner = scanner;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        while (true) {

            wifiScanner.startScan();

            try {
                Thread.sleep(TIME_BETWEEN_SCAN_IN_MILLIS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
