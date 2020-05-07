package com.pds.pgmapp.sensor;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttService {

    Context context;
    String clientId;
    MqttAndroidClient client;

    public MqttService(Context context) {
        this.context = context;
        this.clientId = MqttClient.generateClientId();
        this.client = new MqttAndroidClient(context, "tcp://172.31.249.114:1883", clientId);
    }

    public IMqttToken connectToMqtt() throws MqttException {
        return this.client.connect();
    }

    public void subscribeTopic(String topic, IMqttMessageListener iMqttMessageListener) throws MqttException {
        if (client.isConnected()) {
            this.client.subscribe(topic, 0, iMqttMessageListener);
        }
        else {
            Log.i("MQTT SUBSCRIBE", "subscribe failed") ;
        }
    }
}