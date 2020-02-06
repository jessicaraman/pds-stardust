package com.pds.pgmapp.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pds.pgmapp.R;
import com.pds.pgmapp.geolocation.LocationEntity;
import com.pds.pgmapp.handlers.DBHandler;
import com.pds.pgmapp.handlers.GuidanceHandler;
import com.pds.pgmapp.model.Door;
import com.pds.pgmapp.model.Node;
import com.pds.pgmapp.model.Path;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Guide User Store Activity
 */
public class GuideUserStoreActivity extends AppCompatActivity {
    private DBHandler dbHandler;
    private GuidanceHandler guidanceHandler;
    private TextView locationTextView;
    private TextView vectorDirectionTextView;
    private Timer timer;

    private int nodesCount;
    private int visitedNodesCount;
    private double minimalSignificantDistance = 0.00000000010;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_direction_store);
        dbHandler = DBHandler.getInstance(this);
        guidanceHandler = new GuidanceHandler();
        bindLayout();

        // Fetching regularly user's location
        positionCron();

        // Load Path (mock for now)
        loadPath();
    }

    /**
     * Bind layout res to class variables
     */
    public void bindLayout() {
        locationTextView = findViewById(R.id.locationTextView);
        vectorDirectionTextView = findViewById(R.id.vectorDirectiontextView);
    }

    /**
     * Guide user
     */
    public void guide() {
        // While every node hasn't been reached by user, guidance is not over
        while(this.visitedNodesCount != this.nodesCount) {
            // Finding closed node still to visit
            Node n = this.guidanceHandler.findClosestNode();

            // Indicate the user the closest node and waiting
            showDirection();
            System.out.println("teeeeeeeeeeeeeeeeeeeeeeeeest");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Toast.makeText(getApplicationContext(), "Interrupted", Toast.LENGTH_SHORT).show();
            }

            // If the user reach the node
            if (isNodeReached(n)) {
                // Add the node to reached nodes and counting it
                this.visitedNodesCount++;
                this.guidanceHandler.addReachedNode(n);
            }

            // Waiting
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Toast.makeText(getApplicationContext(), "Interrupted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Show direction to the next point to reach
     */
    public void showDirection() {
        double[] directionVector = this.guidanceHandler.computeDirection();

        // Showing a message (for now)
        setVectorDirectionTextView("direction vector : (" + directionVector[0] + " x ; " + directionVector[1] + ")");
        Log.e("log", "direction vector : (" + directionVector[0] + " x ; " + directionVector[1] + ")");
    }

    /**
     * A node is considered to be reached if the distance from it is under a minimal significant distance
     * @param n node to test
     * @return true or false wether the node is close enough or not
     */
    private boolean isNodeReached(Node n) {
        return (this.guidanceHandler.computeDistance(n, false) < this.minimalSignificantDistance) ;
    }

    /**
     * Loading the user path (currently a mock from a static json)
     */
    public void loadPath() {
        String s = this.getJSONString(this);
        JSONObject jsonPath = this.parseJSON(s);
        Log.e("", s);
        this.guidanceHandler.setPath(new Path(jsonPath, dbHandler));
        this.nodesCount = this.guidanceHandler.getPath().getNodes().size();
        this.visitedNodesCount = 0 ;
    }

    /**
     * Scheduling recurring task (location)
     */
    public void positionCron() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            synchronized public void run() {
                LocationEntity location = dbHandler.getLastLocation();
                if (location != null) {
                    setLocationForGuidance(location);
                    setLocationTextView("location = " + location.toString());
                    Log.e("", "location = " + location.toString());
                    guide();
                } else {
                    Log.e("", "No location registered yet");
                    setLocationTextView("No location registered yet");
                }
            }
        }, TimeUnit.SECONDS.toMillis(1), TimeUnit.SECONDS.toMillis(1));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param location
     */
    public void setLocationForGuidance(LocationEntity location) {
        this.guidanceHandler.setCurrentLocation(location);
    }
    /**
     * Display location string
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

    public void setVectorDirectionTextView(String stringVectorDirection) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                vectorDirectionTextView.setText(stringVectorDirection);
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