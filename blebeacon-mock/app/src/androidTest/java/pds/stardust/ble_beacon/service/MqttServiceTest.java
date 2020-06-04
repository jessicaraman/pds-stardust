package pds.stardust.ble_beacon.service;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class MqttServiceTest {

    MqttService mqttService = new MqttService(ApplicationProvider.getApplicationContext());

    @Test
    public void connectMqttAndPublisMessage() throws MqttException, MqttException {
        try {
            mqttService.connectToMqtt();
            mqttService.messagePublish("topicTest", "messageTest");
        } catch (MqttException e) {
            fail("This test must not fail");
        }
    }
}