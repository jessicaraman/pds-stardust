package com.pds.pgmapp.activity;
import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.pds.pgmapp.R;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import tech.gusavila92.websocketclient.WebSocketClient;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.content.Context.NOTIFICATION_SERVICE;


public class NotificationActivity extends AppCompatActivity {
    private static final String CANAL = "MyNotifCanal";
    private WebSocketClient webSocketClient;
    private String uniqueID ="";
    private int id_notif=1;
    //NotificationCompat.Builder notificationBuilder =new NotificationCompat.Builder(this, CANAL);

    public void makeNotif(String message){
        NotificationCompat.Builder notificationBuilder =new NotificationCompat.Builder(this, CANAL);
        notificationBuilder.setContentTitle("Notification");
        notificationBuilder.setContentText(message);

        //icone
        notificationBuilder.setSmallIcon(R.drawable.mall);

        //envoi de la notif
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId="myCanal";
            String channelTitle="myTitle";
            String channelDescription="test";
            NotificationChannel channel=new NotificationChannel(channelId,channelTitle, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channelDescription);
            notificationManager.createNotificationChannel(channel);
            notificationBuilder.setChannelId(channelId);
            notificationBuilder.setTimeoutAfter(15000);
        }

        notificationManager.notify(id_notif,notificationBuilder.build());
        id_notif++;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notif_divers);
        createWebSocketClient();
    }

    private void createWebSocketClient() {
        URI uri;
        try {
            //adresse websocket à changer !!
            uri = new URI("ws://192.168.1.11:8080/websocket");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                Log.i("WebSocket", "Session is starting");
                webSocketClient.send("Hello World!");
            }

            @Override
            public void onTextReceived(String s) {
                Log.i("WebSocket", "Message received");
                final String message = s;
                makeNotif(message);

            }

            @Override
            public void onBinaryReceived(byte[] data) {
            }

            @Override
            public void onPingReceived(byte[] data) {
            }

            @Override
            public void onPongReceived(byte[] data) {
            }

            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onCloseReceived() {
                Log.i("WebSocket", "Connection Closed ");
                System.out.println("onCloseReceived");
            }
        };

        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
    }


    public void sendMessage(View view) {
        // Send button id string to WebSocket Server
        switch(view.getId()){
            case(R.id.discountButton):
                webSocketClient.send("1");
                break;

            case(R.id.locationButton):
                //webSocketClient.send("2#"+uniqueID);
                webSocketClient.send("2");
                break;

            case(R.id.newsButton):
                //webSocketClient.send("3#"+uniqueID);
                webSocketClient.send("3");
                break;

            case(R.id.otherButton):
                //webSocketClient.send("4#"+uniqueID);
                webSocketClient.send("4");
                break;
        }
    }
}
