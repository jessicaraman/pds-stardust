package com.pds.pgmapp.activity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.pds.pgmapp.R;
import com.pds.pgmapp.exceptions.WifiScanException;
import com.pds.pgmapp.geolocation.Location;
import com.pds.pgmapp.geolocation.LocationEntity;
import com.pds.pgmapp.geolocation.LocationListener;
import com.pds.pgmapp.geolocation.LocationProvider;
import com.pds.pgmapp.geolocation.LocationReceiver;
import com.pds.pgmapp.geolocation.LocationWorker;
import com.pds.pgmapp.handlers.DBHandler;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static com.pds.pgmapp.geolocation.LocationReceiver.DEFAULT_LOCATION_VALUE;

public class LocationActivity extends AppCompatActivity implements LocationListener {

    private TextView locationTextView;
    private LocationReceiver locationReceiver;
    private LocationProvider locationProvider;
    private DBHandler dbHandler;
    private WorkManager workManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocalisation);

        dbHandler = new DBHandler(this);
        workManager = WorkManager.getInstance(this);

        Constraints networkAvailableConstraint = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(LocationWorker.class, PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
                .setConstraints(networkAvailableConstraint)
                .build();

        workManager.enqueue(workRequest);

    }
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onPositionChange(Location location) throws WifiScanException{

        if (location.getX() == DEFAULT_LOCATION_VALUE || location.getY() == DEFAULT_LOCATION_VALUE) {
            throw new WifiScanException("Unable to calculate position");
        }

        dbHandler.saveLocation(
                new LocationEntity(
                        location.getX(),
                        location.getY(),
                        LocalDateTime.now()
                )
        );

        locationTextView = findViewById(R.id.location);

        String str = String.format("X = %s , Y = %s", location.getX(), location.getY()) ;

        locationTextView.setText(str);

    }

    @Override
    protected void onResume() {
        super.onResume();

        locationProvider = new LocationProvider(getApplicationContext());
        locationReceiver = new LocationReceiver(this, this);
    }

    @Override
    protected void onPause() {

        super.onPause();

        locationProvider.unregister();
        locationReceiver.unregister();

    }

}
