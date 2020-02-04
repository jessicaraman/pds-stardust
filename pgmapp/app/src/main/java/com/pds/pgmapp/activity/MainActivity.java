package com.pds.pgmapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;

import com.pds.pgmapp.R;

/**
 * MainActivity : HomePage
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 1;
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_ACCESS_COARSE_LOCATION
            );
        }

        buttonUserGuideStoreAction();
        startGeolocatisation();
    }

    /**
     * Access to countries list
     */
    protected void buttonUserGuideStoreAction() {
        Button buttonCountriesList;
        buttonCountriesList = findViewById(R.id.buttonGuideUserStore);
        buttonCountriesList.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),GuideUserStoreActivity.class)));
    }

    protected void startGeolocatisation() {
        Button startGeolocalisationBtn;
        startGeolocalisationBtn = findViewById(R.id.buttonGeolocalisation);
        startGeolocalisationBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LocationActivity.class)));
    }
}
