package com.pds.pgmapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pds.pgmapp.R;
import com.pds.pgmapp.handlers.AccountHandler;
import com.pds.pgmapp.model.CustomerEntity;

/**
 * Account Activity : Authentication activity (launcher activity)
 */
public class AccountActivity extends AppCompatActivity {
    AccountHandler accountHandler;
    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;

    /**
     * Activity startup : init widgets and event listener
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        this.initWidgets();
        this.loginButton.setOnClickListener(v -> login());
        accountHandler = new AccountHandler(this);
    }

    /**
     * login function called when user press the login button try to authenticate
     */
    private void login() {
        String username = this.usernameEditText.getText().toString().trim();
        String password = this.passwordEditText.getText().toString().trim();

        if (username != null && !username.trim().equals("") && password != null && !password.trim().equals("")) {
            this.accountHandler.connect(username, password);
        } else {
            Log.e(getString(R.string.TAG_ACCOUNT_ACTIVITY), "Wrong username or password");
            Toast.makeText(getApplicationContext(), "Wrong username or password! Try again.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Start main activity when user is successfully authenticated
     *
     * @param loggedCustomer
     */
    public void startMainActivity(CustomerEntity loggedCustomer) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("logged_customer", loggedCustomer);
        startActivity(intent);
    }


    /**
     * Store widgets in class
     */
    private void initWidgets() {
        this.loginButton = findViewById(R.id.loginButton);
        this.usernameEditText = findViewById(R.id.usernameEditText);
        this.passwordEditText = findViewById(R.id.passwordEditText);
    }
}