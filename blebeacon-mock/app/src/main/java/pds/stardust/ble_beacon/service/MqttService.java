package pds.stardust.ble_beacon.service;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

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

    public void messagePublish(String topic, String payload) throws MqttException {
        if (client.isConnected()) {
            MqttMessage mqttMessage = new MqttMessage(payload.getBytes());
            mqttMessage.setRetained(true);
            this.client.publish(topic, mqttMessage);
        }
        else {
            Log.i("MQTT PUBLISH", "publish failed") ;
        }
    }
}


