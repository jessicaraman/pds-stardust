package com.pds.pgmapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WifiReceiver extends BroadcastReceiver {

    private WifiManager wifiManager;
    private ListView listView;

    private HashMap<String, Double> devices;

    private final double[][] APsPositions = new double[][] { { 0, 0 }, { -3.4, 0 }, { 2.8, -1.6 } };

    public WifiReceiver(WifiManager wifiManager, ListView listView) {
        this.wifiManager = wifiManager;
        this.listView = listView;

        devices = new HashMap<>();

        // BIGO
        devices.put("62:f1:89:64:5c:93", 0.0);

        // Iphone de Celine
        devices.put("aa:5b:78:2b:f0:e2", 0.0);

        // AndroidAp
        devices.put("6e:c7:ec:34:6d:4e", 0.0);
    }

    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {

            List<ScanResult> wifiList = wifiManager.getScanResults();

            ArrayList<String> infos = new ArrayList<>();

            for (ScanResult scanResult : wifiList) {

                //if (devices.keySet().contains(scanResult.BSSID)) {
                    double distance = calculateDistance(scanResult.level, scanResult.frequency);
                    devices.put(scanResult.BSSID, distance);
                    infos.add("Distance from " + scanResult.SSID + " = " + distance + " meters");
                //}
            }

            /*
            double[] distances = new double[devices.size()];

            ArrayList<Double> distancesList =  new ArrayList<>(devices.values());

            for (int i = 0; i < distancesList.size(); i++) {
                distances[i] = distancesList.get(i);
            }

            double[] position = definePostionByTrilateration(APsPositions, distances);

            for (double d : position) {
                infos.add("Position : " + d );
            }
            */

            ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, infos.toArray());

            listView.setAdapter(arrayAdapter);
        }
    }

    public double calculateDistance(int signalLevelInDb, double freqInMHz) {
        double exp = (27.55 - (20 * Math.log10(freqInMHz)) + Math.abs(signalLevelInDb)) / 20.0;
        return Math.pow(10.0, exp);
    }

    public double[] definePostionByTrilateration(double[][] positions, double[] distances) {

        NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(positions, distances), new LevenbergMarquardtOptimizer());
        LeastSquaresOptimizer.Optimum optimum = solver.solve();

        return optimum.getPoint().toArray();
    }
}