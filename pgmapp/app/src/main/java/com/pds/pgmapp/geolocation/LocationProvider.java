package com.pds.pgmapp.geolocation;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import com.pds.pgmapp.wifiscan.WifiScanAsyncTask;
import com.pds.pgmapp.wifiscan.WifiScanResultListener;
import com.pds.pgmapp.wifiscan.WifiScanResultReceiver;
import com.pds.pgmapp.wifiscan.WifiScanner;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;

public class LocationProvider implements WifiScanResultListener {

    private Context context;

    private WifiScanner wifiScanner;
    private WifiScanResultReceiver wifiReceiver;
    private WifiScanAsyncTask wifiScanTask;

    private HashMap<String, Double> accessPoints;

    private final String AP1 = "a2:fa:c4:2c:39:04";
    private final String AP2 = "4c:d1:a1:30:a0:20";
    private final String AP3 = "a2:fa:c4:2c:39:06";

    private final double[][] APsPositions = new double[][] { { 0, 0 }, { -3.4, 0 }, { 2.8, -1.6 } };

    private final double DEFAULT_DISTANCE = 0.0;

    public LocationProvider(Context context) {

        this.context = context;

        accessPoints = new HashMap<>();
        accessPoints.put(AP1, DEFAULT_DISTANCE);
        accessPoints.put(AP2, DEFAULT_DISTANCE);
        accessPoints.put(AP3, DEFAULT_DISTANCE);

        wifiScanner = new WifiScanner(context);

        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);

        wifiReceiver = new WifiScanResultReceiver(context, wifiManager, this);

        if (null == wifiScanTask) {

            wifiScanTask = new WifiScanAsyncTask(wifiScanner);

        } else {

            if (AsyncTask.Status.FINISHED.equals(wifiScanTask.getStatus())) {

                wifiScanTask = new WifiScanAsyncTask(wifiScanner);

            }
        }

        wifiScanTask.execute();

    }

    @Override
    public void onWifiScanReceive(List<ScanResult> scanResults) {

        for (ScanResult scanResult : scanResults) {

            if (accessPoints.keySet().contains(scanResult.BSSID)) {
                double distance = calculateDistance(scanResult.level, scanResult.frequency);
                accessPoints.put(scanResult.BSSID, distance);
            }

        }

        double[] distances = new double[accessPoints.size()];

        ArrayList<Double> apsDistances =  new ArrayList<>(accessPoints.values());

        for (int i = 0; i < apsDistances.size(); i++) {
            distances[i] = apsDistances.get(i);
        }

        Location location = calculateLocationByTrilateration(APsPositions, distances);

        Log.d("LocationProvider",location.getX() + " , " + location.getY());

        Intent intent = new Intent("LOCATION");
        intent.putExtra("x", location.getX());
        intent.putExtra("y", location.getY());
        context.sendBroadcast(intent);

    }

    public void unregister() {
        context.unregisterReceiver(wifiReceiver);
    }

    private double calculateDistance(int signalLevelInDb, double freqInMHz) {
        double exp = (27.55 - (20 * Math.log10(freqInMHz)) + Math.abs(signalLevelInDb)) / 20.0;
        return Math.pow(10.0, exp);
    }

    private Location calculateLocationByTrilateration(double[][] positions, double[] distances) {

        NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(positions, distances), new LevenbergMarquardtOptimizer());
        LeastSquaresOptimizer.Optimum optimum = solver.solve();

        double[] coordinates = optimum.getPoint().toArray();

        return new Location(coordinates[0], coordinates[1]);
    }
}
