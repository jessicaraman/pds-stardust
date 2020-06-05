package com.pds.pgmapp.sensor;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class MqttServiceTest {

    MqttService mqttService = new MqttService(ApplicationProvider.getApplicationContext());

    @Test
    public void connectMqttAndSubscribeTopic() {
        try {
            mqttService.connectToMqtt();
            mqttService.subscribeTopic("topicTest", (topic, message) -> {
                assertEquals("messageTest", message);
            });
        } catch (MqttException e) {
            fail("This test must not fail");
        }
    }
}