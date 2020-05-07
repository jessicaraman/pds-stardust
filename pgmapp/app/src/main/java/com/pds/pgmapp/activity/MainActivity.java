package com.pds.pgmapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.pds.pgmapp.R;
import com.pds.pgmapp.handlers.FirebaseHandler;
import com.pds.pgmapp.model.CustomerEntity;

/**
 * MainActivity : HomePage
 */
public class MainActivity extends AppCompatActivity {

    private CustomerEntity loggedCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loggedCustomer();
        setContentView(R.layout.activity_main);
        buttonUserGuideStoreAction();
        buttonPath();
        startGeolocatisation();
        startBeaconCapting();
        initFirebase();
    }

    protected void loggedCustomer() {
        Intent intent = getIntent();
        loggedCustomer = intent.getParcelableExtra("logged_customer");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 1;
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_ACCESS_COARSE_LOCATION
            );
        }
        Toast.makeText(getApplicationContext(), "Bonjour " + loggedCustomer.getUsername() + "!", Toast.LENGTH_SHORT).show();
    }

    protected void buttonUserGuideStoreAction() {
        Button buttonCountriesList;
        buttonCountriesList = findViewById(R.id.buttonGuideUserStore);
        buttonCountriesList.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), GuideUserStoreActivity.class)));
    }

    protected void buttonPath() {
        Button btn;
        btn = findViewById(R.id.buttonPath);
        btn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), PathActivity.class)));
    }

    protected void startGeolocatisation() {
        Button startGeolocalisationBtn;
        startGeolocalisationBtn = findViewById(R.id.buttonGeolocalisation);
        startGeolocalisationBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LocationActivity.class)));
    }

    protected void startBeaconCapting() {
        Button startBeaconCaptingButton;
        startBeaconCaptingButton = findViewById(R.id.buttonBeacon);
        startBeaconCaptingButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), BeaconActivity.class)));
    }

    protected void initFirebase() {
        FirebaseHandler firebaseHandler = new FirebaseHandler();
    }
}
