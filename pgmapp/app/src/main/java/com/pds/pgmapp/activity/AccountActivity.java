package com.pds.pgmapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.pds.pgmapp.R;
import com.pds.pgmapp.handlers.Constant;
import com.pds.pgmapp.model.CustomerEntity;
import com.pds.pgmapp.retrofit.AccountService;
import com.pds.pgmapp.retrofit.RetrofitInstance;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Account Activity : Authentication activity (launcher activity)
 */
public class AccountActivity extends AppCompatActivity {

    AccountService apiAccountService;
    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    CustomerEntity loggedCustomer;

    /**
     * Activity startup : init widgets and event listener
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        this.initWidgets();
        this.loginButton.setOnClickListener(v -> login());
    }

    /**
     * login function called when user press the login button try to authenticate
     */
    private void login() {
        String username = this.usernameEditText.getText().toString().trim();
        String password = this.passwordEditText.getText().toString().trim();

        if (username != null && !username.trim().equals("") && password != null && !password.trim().equals("")) {
            this.connect(username, password);
        } else {
            Log.e(Constant.TAG_ACCOUNT_ACTIVITY, "Wrong username or password");
            Toast.makeText(getApplicationContext(), "Wrong username or password! Try again.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * connection function, call remote API and try to authenticate with given credentials
     */
    private void connect(String username, String password) {
        apiAccountService = RetrofitInstance.getRetrofitInstance().create(AccountService.class);
        CustomerEntity loginCustomer = new CustomerEntity();
        loginCustomer.setUsername(username);
        loginCustomer.setPassword(password);
        Call<ResponseBody> heartbeatCall = apiAccountService.heartbeat();
        Call<CustomerEntity> connectCall = apiAccountService.connect(RequestBody.create(MediaType.parse("json"), "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }"));

        // hearbeat app
        heartbeatCall.enqueue(((new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(Constant.TAG_ACCOUNT_ACTIVITY, "Heartbeat response = " + response.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(Constant.TAG_ACCOUNT_ACTIVITY, "Exception while network heartbeat :" + t.getMessage() + " caused by " + t.getCause());
            }
        })));

        // connection app
        connectCall.enqueue((new Callback<CustomerEntity>() {
            @Override
            public void onResponse(Call<CustomerEntity> call, Response<CustomerEntity> response) {
                try {
                    if (response.isSuccessful()) {
                        Log.i(Constant.TAG_ACCOUNT_ACTIVITY, response.message());
                        loggedCustomer = response.body();
                        if (loggedCustomer != null && (loggedCustomer.getUsername() != null && loginCustomer.getUsername().equals(username) && loggedCustomer.getPassword() != null && loginCustomer.getPassword().equals(password))) {
                            firebaseInstanceTokenId();
                        } else {
                            Log.e(Constant.TAG_ACCOUNT_ACTIVITY, "Authentication failed : " + response.errorBody().string());
                            Toast.makeText(getApplicationContext(), "Authentication failed! Please retry later. 1", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e(Constant.TAG_ACCOUNT_ACTIVITY, "Authentication failed : " + response.errorBody().string());
                        Toast.makeText(getApplicationContext(), "Wrong username or password! Try again.", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException ex) {
                    Log.e(Constant.TAG_ACCOUNT_ACTIVITY, "Connection response error " + ex.getMessage());
                    Toast.makeText(getApplicationContext(), "Connection error, please retry later. 3", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CustomerEntity> call, Throwable t) {
                Log.e(Constant.TAG_ACCOUNT_ACTIVITY, "Network failure : " + t.getMessage() + " caused by " + t.getCause());
                Log.e(Constant.TAG_ACCOUNT_ACTIVITY, "Requested URL was : " + call.request().url());
                Log.e(Constant.TAG_ACCOUNT_ACTIVITY, "Request body was : " + call.request().toString());
            }
        }));
    }

    /**
     * Store widgets in class
     */
    private void initWidgets() {
        this.loginButton = findViewById(R.id.loginButton);
        this.usernameEditText = findViewById(R.id.usernameEditText);
        this.passwordEditText = findViewById(R.id.passwordEditText);
    }

    /**
     * Generate a unique Firebase Cloud Messaging Token ID
     * Get the Instance ID token
     */
    public void firebaseInstanceTokenId() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    String token = task.getResult().getToken();
                    String msg = getString(R.string.fcm_token, token);
                    Log.i(Constant.TAG_ACCOUNT_ACTIVITY, msg);
                    if (!task.isSuccessful()) {
                        Log.e(Constant.TAG_ACCOUNT_ACTIVITY, msg);
                    } else {
                        this.updateToken(token);
                        Log.i(Constant.TAG_ACCOUNT_ACTIVITY, "Updated token successfully");
                    }
                });
    }

    /**
     * Update the user's Firebase Cloud Messaging Token
     */
    private void updateToken(String token) {
        loggedCustomer.setToken(token);
        Call<CustomerEntity> updateCall = apiAccountService.updateToken(loggedCustomer);
        updateCall.enqueue(new Callback<CustomerEntity>() {
            @Override
            public void onResponse(Call<CustomerEntity> call, Response<CustomerEntity> response) {
                Log.i(Constant.TAG_ACCOUNT_ACTIVITY, "Updated token successfully, lauching MainActivity");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<CustomerEntity> call, Throwable t) {
                Log.e(Constant.TAG_ACCOUNT_ACTIVITY, "Update token failure : " + t.getMessage() + " caused by " + t.getCause());
            }
        });
    }
}