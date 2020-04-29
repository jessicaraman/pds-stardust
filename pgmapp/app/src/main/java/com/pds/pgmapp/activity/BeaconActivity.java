package com.pds.pgmapp.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.pds.pgmapp.R;
import com.pds.pgmapp.geolocation.LocationHistoryEntity;
import com.pds.pgmapp.model.CustomerEntity;
import com.pds.pgmapp.model.PassageEntity;
import com.pds.pgmapp.retrofit.FrequentationService;
import com.pds.pgmapp.retrofit.RetrofitInstance;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeaconActivity extends Activity implements BeaconConsumer {

    protected static final String TAG = "MonitoringActivity";
    protected static final String TAG2 = "RangingActivity";

    public static final String ALTBEACON = "m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25";
    public static final String ALTBEACON2 = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25";

    public NotificationChannel channel;
    public Notification notification;
    private static final String NOTIFICATION_CHANNEL_ID = "pgmapp";

    private HashMap<String, Date> recentlyVisitedBeacons = new HashMap <String, Date> ();;

    private BeaconManager beaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);

        beaconManager = BeaconManager.getInstanceForApplication(this);

        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(ALTBEACON));
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(ALTBEACON2));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
        createNotification();

        beaconManager.enableForegroundServiceScanning(notification , 456);
        beaconManager.setEnableScheduledScanJobs(false);

        beaconManager.setBackgroundBetweenScanPeriod(0);
        beaconManager.setBackgroundScanPeriod(1100);

        beaconManager.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {

        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.i(TAG, "I just saw an beacon for the first time!");
            }

            @Override
            public void didExitRegion(Region region) {
                Log.i(TAG, "I no longer see an beacon");
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.i(TAG, "I have just switched from seeing/not seeing beacons: "+state);
            }
        });

        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                Date currentDate = new Date();
                for(Beacon beacon : beacons) {
                    if(!recentlyVisitedBeacons.containsKey(beacon.getId1().toString())) {
                        recentlyVisitedBeacons.put(beacon.getId1().toString(), currentDate);
                        PassageEntity passage = new PassageEntity(1, beacon.getId1().toString(), currentDate, beacon.getDistance());
                        sendDataToMicroService(passage);
                    } else {
                        if(recentlyVisitedBeacons.get(beacon.getId1().toString()).compareTo(new Date(currentDate.getTime() - 3600 * 1000)) < 0) {
                            recentlyVisitedBeacons.remove(beacon.getId1().toString());
                            recentlyVisitedBeacons.put(beacon.getId1().toString(), currentDate);
                            PassageEntity passage = new PassageEntity(1, beacon.getId1().toString(), currentDate, beacon.getDistance());
                            sendDataToMicroService(passage);
                        }
                    }
                }
                //for(String key : recentlyVisitedBeacons.keySet()) {
                  //  System.out.println("Beacon : " + key + " à telle date : " + recentlyVisitedBeacons.get(key));
                //}

            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
        } catch (RemoteException e) {    }
        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void sendDataToMicroService(PassageEntity passage) {
        Gson gson = new Gson();
        FrequentationService frequentationService = RetrofitInstance.getRetrofitInstance().create(FrequentationService.class);
        Call<ResponseBody> postCall = frequentationService.postFrequentationData(passage);

        postCall.enqueue(((new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("SUCCESS FREQUENTATION", "response = " + response.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("FAILURE FREQUENTATION", "response = " + t.toString());
            }
        })));
    }





    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "PGMAPP";
            String description = "PGMAPP is scanning for beacons";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void createNotification() {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Intent intent = new Intent(this, BeaconActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Scanning for Beacons")
                .setContentIntent(pendingIntent)
                .build();
    }
}