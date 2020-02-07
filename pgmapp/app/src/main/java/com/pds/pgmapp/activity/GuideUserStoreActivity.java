package com.pds.pgmapp.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pds.pgmapp.R;
import com.pds.pgmapp.geolocation.LocationEntity;
import com.pds.pgmapp.handlers.DBHandler;
import com.pds.pgmapp.handlers.GuidanceHandler;
import com.pds.pgmapp.model.Node;
import com.pds.pgmapp.model.Path;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Timer;

/**
 * Guide User Store Activity
 */
public class GuideUserStoreActivity extends AppCompatActivity {
    private DBHandler dbHandler;
    private GuidanceHandler guidanceHandler;
    private TextView locationTextView;
    private TextView vectorDirectionTextView;
    private TextView visitedNodesTextView;
    private Timer timer;

    private int nodesCount;
    private int visitedNodesCount;
    // almost all location match
    //private double minimalSignificantDistance = 1 ; //0.00000000010;
    // mid precision
    private double minimalSignificantDistance = 0.5; //0.00000000010;
    // very precise location match
    //private double minimalSignificantDistance = 0.00000000010;

    private boolean guidanceActive;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_direction_store);
        dbHandler = DBHandler.getInstance(this);
        guidanceHandler = new GuidanceHandler();
        bindLayout();

        // Fetching regularly user's location
        loadPath();
        this.guidanceActive = true;

        Button startButton;
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> guide());
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.guidanceActive = false;
    }

    /**
     * Bind layout res to class variables
     */
    public void bindLayout() {
        locationTextView = findViewById(R.id.locationTextView);
        vectorDirectionTextView = findViewById(R.id.vectorDirectiontextView);
        visitedNodesTextView = findViewById(R.id.visitedNodesTextView);
    }

    /**
     * Guide user
     */
    public void guide() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                clearVisitedNodesTextView();
                // While every node hasn't been reached by user, guidance is not over
                while (visitedNodesCount != nodesCount && guidanceActive) {
                    // read from db
                    //LocationEntity location = dbHandler.getLastLocation();
                    // mock
                    double x = (Math.random() * 2) - 1;
                    double y = (Math.random() * 2) - 1;
                    LocationEntity location = new LocationEntity(x, y, LocalDateTime.now());

                    if (location != null) {
                        setLocationForGuidance(location);
                        setLocationTextView("location = " + location.toString());
                        Log.e("", "location = " + location.toString());

                        // Finding closest node still to visit
                        Node n = guidanceHandler.findClosestNode();
                        Log.e("log", "affiche le n" + n);

                        // Indicate the user the closest node and waiting
                        showDirection(n);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            toast("Interrupted");
                        }

                        // If the user reach the node
                        if (isNodeReached(n)) {
                            // Add the node to reached nodes and counting it
                            visitedNodesCount++;
                            guidanceHandler.addReachedNode(n);
                            appendVisitedNode("Node reached : " + n.getLabel() + "\n");
                            toast("Node reached : " + n.getLabel());
                        }

                        // Waiting
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            toast("Interrupted");
                        }
                    } else {
                        toast("No location registered yet");
                        setLocationTextView("No location registered yet");
                    }
                }
                toast("Path is terminated have a nice day !");
            }
        };
        thread.start();
    }

    /**
     * Show direction to the next point to reach
     */
    public void showDirection(Node n) {
        double[] directionVector = this.guidanceHandler.computeDirection();
        String directionMsg = "Next node to reach : " + n.getLabel() + " \n" + "Direction vector : (" + directionVector[0] + " x ; " + directionVector[1] + " y)";
        // Showing a message (for now)
        setVectorDirectionTextView(directionMsg);
        Log.e("log", directionMsg);
    }

    /**
     * A node is considered to be reached if the distance from it is under a minimal significant distance
     *
     * @param n node to test
     * @return true or false wether the node is close enough or not
     */
    private boolean isNodeReached(Node n) {
        return (this.guidanceHandler.computeDistance(n, false) < this.minimalSignificantDistance);
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
        this.visitedNodesCount = 0;
    }

    /**
     * @param location
     */
    public void setLocationForGuidance(LocationEntity location) {
        System.out.println("SetLocationForGuidance : " + location.toString());
        this.guidanceHandler.setCurrentLocation(location);
    }

    /**
     * Display location string
     *
     * @param stringLocation
     */
    public void setLocationTextView(String stringLocation) {
        runOnUiThread(() -> locationTextView.setText(stringLocation));
    }

    public void setVectorDirectionTextView(String stringVectorDirection) {
        runOnUiThread(() -> vectorDirectionTextView.setText(stringVectorDirection));
    }

    public void clearVisitedNodesTextView() {
        runOnUiThread(() -> visitedNodesTextView.setText(""));
    }

    public void appendVisitedNode(String text) {
        runOnUiThread(() -> visitedNodesTextView.append(text));
    }

    public void toast(String msg) {
        runOnUiThread((() -> {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            Log.i("INFO", msg);
        }));
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
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.guidanceActive = true;
    }
}