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

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Account Activity : Authentication activity (launcher activity)
 */
public class AccountActivity extends AppCompatActivity {
    AccountHandler accountHandler;
    CustomerEntity customerEntity;
    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    boolean heartbeat;

    // ***********
    // Callbacks :
    // ***********
    Callback<ResponseBody> heartbeatCallback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            Log.i(getString(R.string.TAG_ACCOUNT_HANDLER), "Heartbeat response = " + response.toString());
            heartbeat = true;
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            Log.e(getString(R.string.TAG_ACCOUNT_HANDLER), "Exception while network heartbeat :" + t.getMessage() + " caused by " + t.getCause());
            heartbeat = false;
        }
    };
    Callback<CustomerEntity> updateTokenCallback = new Callback<CustomerEntity>() {
        @Override
        public void onResponse(Call<CustomerEntity> call, Response<CustomerEntity> response) {
            Log.i(getString(R.string.TAG_ACCOUNT_HANDLER), "Updated token successfully, launching MainActivity");
            startMainActivity(customerEntity);
        }

        @Override
        public void onFailure(Call<CustomerEntity> call, Throwable t) {
            Log.e(getString(R.string.TAG_ACCOUNT_HANDLER), "Update token failure : " + t.getMessage() + " caused by " + t.getCause());
        }
    };
    Callback<CustomerEntity> connectCallback = new Callback<CustomerEntity>() {
        @Override
        public void onResponse(Call<CustomerEntity> call, Response<CustomerEntity> response) {
            try {
                if (response.isSuccessful()) {
                    Log.i(getString(R.string.TAG_ACCOUNT_HANDLER), response.message());
                    CustomerEntity loggedCustomer = response.body();
                    if (loggedCustomer != null && (loggedCustomer.getUsername() != null && loggedCustomer.getUsername().equals(customerEntity.getUsername()) && loggedCustomer.getPassword() != null && loggedCustomer.getPassword().equals(customerEntity.getPassword()))) {
                        accountHandler.setLoggedCustomer(loggedCustomer);
                        accountHandler.setUpdateTokenCallback(updateTokenCallback);
                        accountHandler.firebaseInstanceTokenId();
                    } else {
                        Log.e(getString(R.string.TAG_ACCOUNT_HANDLER), "Authentication failed : " + response.errorBody().string());
                        Toast.makeText(getApplicationContext(), "Authentication failed! Please retry later. 1", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(getString(R.string.TAG_ACCOUNT_HANDLER), "Authentication failed : " + response.errorBody().string());
                    Toast.makeText(getApplicationContext(), "Wrong username or password! Try again.", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException ex) {
                Log.e(getString(R.string.TAG_ACCOUNT_HANDLER), "Connection response error " + ex.getMessage());
                Toast.makeText(getApplicationContext(), "Connection error, please retry later. 3", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<CustomerEntity> call, Throwable t) {
            Log.e(getString(R.string.TAG_ACCOUNT_HANDLER), "Network failure : " + t.getMessage() + " caused by " + t.getCause());
            Log.e(getString(R.string.TAG_ACCOUNT_HANDLER), "Requested URL was : " + call.request().url());
            Log.e(getString(R.string.TAG_ACCOUNT_HANDLER), "Request body was : " + call.request().toString());
        }
    };

    /**
     * Activity startup : init widgets and event listener
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        this.initWidgets();
        this.loginButton.setOnClickListener(v -> {
            login();
        });
        accountHandler = new AccountHandler(this);
        accountHandler.asyncHeartbeat(heartbeatCallback);
    }

    /**
     * Login function called when user press the login button try to authenticate
     */
    private void login() {
        if (!this.heartbeat) {
            Log.e(getString(R.string.TAG_ACCOUNT_ACTIVITY), "No network access when trying to authenticate");
            Toast.makeText(getApplicationContext(), "No network access.", Toast.LENGTH_SHORT).show();
            return;
        }
        String username = this.usernameEditText.getText().toString().trim();
        String password = this.passwordEditText.getText().toString().trim();
        if (username != null && !username.trim().equals("") && password != null && !password.trim().equals("")) {
            customerEntity = new CustomerEntity();
            customerEntity.setUsername(username);
            customerEntity.setPassword(password);
            this.accountHandler.asyncConnect(customerEntity, connectCallback);
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