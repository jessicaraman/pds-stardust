package com.pds.pgmapp.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pds.pgmapp.R;
import com.pds.pgmapp.geolocation.LocationEntity;
import com.pds.pgmapp.handlers.DBHandler;
import com.pds.pgmapp.model.Path;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Guide User Store Activity
 */
public class GuideUserStoreActivity extends AppCompatActivity {

    private Timer timer;
    private DBHandler dbHandler;
    private LocationEntity locationEntity;
    private TextView locationTextView;
    private Path path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_direction_store);
        dbHandler = DBHandler.getInstance(this);
        bindLayout();
        positionCron();
        loadPath();
    }

    public void bindLayout() {
        locationTextView = findViewById(R.id.locationTextView);
    }

    public void loadPath() {
        String s = this.getJSONString(this);
        JSONObject jsonPath = this.parseJSON(s);
        Log.e("", s);
        this.path = new Path(jsonPath, dbHandler);
        this.path.getNodes();
    }

    /**
     * Scheduling recurring task (location)
     */
    public void positionCron() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            synchronized public void run() {
                locationEntity = dbHandler.getLastLocation();
                if (locationEntity != null) {
                    setLocationTextView("location = " + locationEntity.toString());
                    Log.e("", "location = " + locationEntity.toString());
                } else {
                    Log.e("", "No location registered yet");
                    setLocationTextView("No location registered yet");
                }
            }
        }, TimeUnit.SECONDS.toMillis(1), TimeUnit.SECONDS.toMillis(1));
    }

    /**
     * Display string location
     *
     * @param stringLocation
     */
    public void setLocationTextView(String stringLocation) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                locationTextView.setText(stringLocation);
            }
        });
    }

    /**
     * Read json file
     *
     * @param context
     * @return
     */
    private String getJSONString(Context context) {
        String str = "";
        try {
            AssetManager assetManager = context.getAssets();
            InputStream in = assetManager.open("path1.json");
            InputStreamReader isr = new InputStreamReader(in);
            char[] inputBuffer = new char[100];

            int charRead;
            while ((charRead = isr.read(inputBuffer)) > 0) {
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return str;
    }

    /**
     * Parse json file
     *
     * @param raw
     * @return
     */
    public JSONObject parseJSON(String raw) {
        JSONObject json = new JSONObject();
        try {
            json = new JSONObject(raw);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
        //implement logic with JSON here
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
