package com.pds.pgmapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pds.pgmapp.R;
import com.pds.pgmapp.model.Node;

/**
 * Activity : Guide User Store
 */
public class GuideUserStoreActivity extends AppCompatActivity implements View.OnClickListener {

    Node[] pathMocked = new Node[5];
    int xMocked = 0;
    int yMocked = 0;
    int currentNode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_direction_store);

        Button down = findViewById(R.id.goBack);
        Button left = findViewById(R.id.left);
        Button right = findViewById(R.id.right);
        Button high = findViewById(R.id.high);
        down.setOnClickListener(this);
        left.setOnClickListener(this);
        high.setOnClickListener(this);
        right.setOnClickListener(this);

        mockPath();
        TextView direction = findViewById(R.id.direction);
        direction.setText(pathMocked[currentNode].getDirection());
    }

    public void mockPath() {
        pathMocked[0] = new Node(0, 0, "straight ahead");
        pathMocked[1] = new Node(0, 10, "right");
        pathMocked[2] = new Node(10, 10, "straight ahead");
        pathMocked[3] = new Node(10, 20, "left");
        pathMocked[4] = new Node(0, 20, "arrived");
    }

    @Override
    public void onClick(View view) {
        System.out.println(view.getId());
        String directionTaken = null;
        switch (view.getId()) {
            case R.id.goBack:
                yMocked = yMocked - 5;
                directionTaken = "back";
                break;
            case R.id.left:
                xMocked = xMocked - 5;
                directionTaken = "left";
                break;
            case R.id.right:
                xMocked = xMocked + 5;
                directionTaken = "right";
                break;
            case R.id.high:
                yMocked = yMocked + 5;
                directionTaken = "straight ahead";
                break;
        }

        if (currentNode < 3) {
            if (yMocked == pathMocked[currentNode + 1].getY() && xMocked == pathMocked[currentNode + 1].getX()) {
                TextView direction = findViewById(R.id.direction);
                direction.setText(pathMocked[currentNode + 1].getDirection());
                currentNode++;
            }
        } else if (yMocked == pathMocked[4].getY() && xMocked == pathMocked[4].getX()) {
            TextView direction = findViewById(R.id.direction);
            direction.setText("arrived");
            Button down = findViewById(R.id.goBack);
            Button left = findViewById(R.id.left);
            Button right = findViewById(R.id.right);
            Button high = findViewById(R.id.high);
            down.setVisibility(View.INVISIBLE);
            left.setVisibility(View.INVISIBLE);
            right.setVisibility(View.INVISIBLE);
            high.setVisibility(View.INVISIBLE);
        }
    }
}