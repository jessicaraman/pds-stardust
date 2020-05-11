package pds.stardust.ble_beacon.activity;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.util.Arrays;

import pds.stardust.ble_beacon.R;
import pds.stardust.ble_beacon.entity.SensorEntity;
import pds.stardust.ble_beacon.retrofit.RetrofitInstance;
import pds.stardust.ble_beacon.retrofit.SensorDataService;
import pds.stardust.ble_beacon.service.MqttService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String SENSOR_ID = "eb071777-10a9-401c-9323-d8e70e0968c1";

    BeaconManager beaconManager;
    BeaconConsumer beaconConsumer;
    MqttService mqttService;
    String mqttTopicLabel;
    String mqttTopicId;
    String mqttMessage;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorDataService sensorDataService = RetrofitInstance.getRetrofitInstance().create(SensorDataService.class);

        Call<SensorEntity> call = sensorDataService.getSensorById(SENSOR_ID);
        call.enqueue(new Callback<SensorEntity>() {
            @Override
            public void onResponse(Call<SensorEntity> call, Response<SensorEntity> response) {
                Log.d("RETROFIT CALL", "CALLBACK onResponse");
                    if (response.isSuccessful()) {

                        SensorEntity sensor = response.body();

                        MainActivity.this.mqttTopicLabel = sensor.getTopic().getLabel();
                        MainActivity.this.mqttTopicId = sensor.getTopic().getId();
                        MainActivity.this.mqttMessage = sensor.getMessage();

                        MainActivity.this.initSensor();
                    }
            }

            @Override
            public void onFailure(Call<SensorEntity> call, Throwable t) {
                Log.d("RETROFIT CALL", "CALLBACK onFailure");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(beaconConsumer);
    }

    public void initSensor() {
        beaconManager = BeaconManager.getInstanceForApplication(this);
        // beaconManager.getBeaconParsers().clear();
        BeaconParser beaconParser = new BeaconParser().setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25");
        //  beaconManager.getBeaconParsers().add(beaconParser);

        Beacon beacon = new Beacon.Builder()
                .setId1(mqttTopicId)
                .setId2("2")
                .setId3("3")
                .setManufacturer(0x0118)
                .setTxPower(-59)
                .setDataFields(Arrays.asList(new Long[] {0l}))
                .build();

        BeaconTransmitter beaconTransmitter = new BeaconTransmitter(getApplicationContext(), beaconParser);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            beaconTransmitter.startAdvertising(beacon, new AdvertiseCallback() {

                @Override
                public void onStartFailure(int errorCode) {
                    Toast.makeText(getApplicationContext(), "Start failed : " + errorCode, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                    Toast.makeText(getApplicationContext(), "Started with success", Toast.LENGTH_LONG).show();
                    try {
                        mqttService = new MqttService(getApplicationContext());

                        mqttService.connectToMqtt().setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                try {
                                    MainActivity.this.mqttService.messagePublish(mqttTopicLabel, mqttMessage);
                                } catch (MqttException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}