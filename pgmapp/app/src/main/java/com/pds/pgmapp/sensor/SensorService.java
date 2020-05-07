package com.pds.pgmapp.sensor;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.pds.pgmapp.geolocation.LocationHistoryEntity;
import com.pds.pgmapp.retrofit.LocationDataService;
import com.pds.pgmapp.retrofit.RetrofitInstance;
import com.pds.pgmapp.retrofit.SensorDataService;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SensorService implements BeaconConsumer {

    private BeaconManager beaconManager;
    private MqttService mqttService;

    public SensorService(BeaconManager beaconManager) {
        this.beaconManager = beaconManager;
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.removeAllRangeNotifiers();
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    for (Beacon beacon : beacons) {
                        Log.d("BEACON LOG","Beacon [" + beacon.getId1() + "] : " + String.format("%.2f", beacons.iterator().next().getDistance()) + " meters");

                        SensorDataService sensorDataService = RetrofitInstance.getRetrofitInstance().create(SensorDataService.class);

                        Call<SensorLabelResponse> call = sensorDataService.getLabelByTopicId(beacon.getId1().toString());

                        call.enqueue(new Callback<SensorLabelResponse>() {
                            @Override
                            public void onResponse(Call<SensorLabelResponse> call, Response<SensorLabelResponse> response) {
                                Log.d("RETROFIT CALL", "CALLBACK onResponse");
                                try {
                                    if (response.isSuccessful()) {
                                        SensorService.this.mqttService.subscribeTopic(response.body().getData(), (topic, message) -> Log.d("MESSAGE ARRIVED", new String(message.getPayload())));
                                    }
                                } catch (MqttException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<SensorLabelResponse> call, Throwable t) {
                                Log.d("RETROFIT CALL", "CALLBACK onFailure");
                            }
                        });
                    }

                }
                else {
                    Log.d("BEACON LOG","No beacons found !");
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("regionUniqueId", null, null, null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Context getApplicationContext() {
        return null;
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {}

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return false;
    }
}

