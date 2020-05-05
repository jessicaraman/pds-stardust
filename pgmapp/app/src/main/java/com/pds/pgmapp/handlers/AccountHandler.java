package com.pds.pgmapp.handlers;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.pds.pgmapp.R;
import com.pds.pgmapp.activity.AccountActivity;
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
 * AccountHandler : handles account management : connection and token update
 */
public class AccountHandler {
    private AccountActivity activity;
    private AccountService apiAccountService;
    private CustomerEntity loggedCustomer;

    /**
     * Init retrofit consumer API
     * @param a
     */
    public AccountHandler(AccountActivity a) {
        activity = a;
        apiAccountService = RetrofitInstance.getRetrofitInstance().create(AccountService.class);
    }

    /**
     * Connection function, call remote API and try to authenticate with given credentials
     * @param username customer login
     * @param password customer password
     */
    public void connect(String username, String password) {
        CustomerEntity loginCustomer = new CustomerEntity();
        loginCustomer.setUsername(username);
        loginCustomer.setPassword(password);
        Call<ResponseBody> heartbeatCall = apiAccountService.heartbeat();
        Call<CustomerEntity> connectCall = apiAccountService.connect(RequestBody.create(MediaType.parse("json"), "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }"));

        // hearbeat app
        heartbeatCall.enqueue(((new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(activity.getString(R.string.TAG_ACCOUNT_HANDLER), "Heartbeat response = " + response.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(activity.getString(R.string.TAG_ACCOUNT_HANDLER), "Exception while network heartbeat :" + t.getMessage() + " caused by " + t.getCause());
            }
        })));

        // connection app
        connectCall.enqueue((new Callback<CustomerEntity>() {
            @Override
            public void onResponse(Call<CustomerEntity> call, Response<CustomerEntity> response) {
                try {
                    if (response.isSuccessful()) {
                        Log.i(activity.getString(R.string.TAG_ACCOUNT_HANDLER), response.message());
                        loggedCustomer = response.body();
                        if (loggedCustomer != null && (loggedCustomer.getUsername() != null && loginCustomer.getUsername().equals(username) && loggedCustomer.getPassword() != null && loginCustomer.getPassword().equals(password))) {
                            firebaseInstanceTokenId();
                        } else {
                            Log.e(activity.getString(R.string.TAG_ACCOUNT_HANDLER), "Authentication failed : " + response.errorBody().string());
                            Toast.makeText(activity.getApplicationContext(), "Authentication failed! Please retry later. 1", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e(activity.getString(R.string.TAG_ACCOUNT_HANDLER), "Authentication failed : " + response.errorBody().string());
                        Toast.makeText(activity.getApplicationContext(), "Wrong username or password! Try again.", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException ex) {
                    Log.e(activity.getString(R.string.TAG_ACCOUNT_HANDLER), "Connection response error " + ex.getMessage());
                    Toast.makeText(activity.getApplicationContext(), "Connection error, please retry later. 3", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerEntity> call, Throwable t) {
                Log.e(activity.getString(R.string.TAG_ACCOUNT_HANDLER), "Network failure : " + t.getMessage() + " caused by " + t.getCause());
                Log.e(activity.getString(R.string.TAG_ACCOUNT_HANDLER), "Requested URL was : " + call.request().url());
                Log.e(activity.getString(R.string.TAG_ACCOUNT_HANDLER), "Request body was : " + call.request().toString());
            }
        }));
    }

    /**
     * Generate a unique Firebase Cloud Messaging Token ID
     * Get the Instance ID token
     */
    public void firebaseInstanceTokenId() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    String token = task.getResult().getToken();
                    String msg = activity.getString(R.string.fcm_token, token);
                    Log.i(activity.getString(R.string.TAG_ACCOUNT_HANDLER), msg);
                    if (!task.isSuccessful()) {
                        Log.e(activity.getString(R.string.TAG_ACCOUNT_HANDLER), msg);
                    } else {
                        this.updateToken(token);
                        Log.i(activity.getString(R.string.TAG_ACCOUNT_HANDLER), "Updated token successfully");
                    }
                });
    }


    /**
     * Update the user's Firebase Cloud Messaging Token
     * @param token FCM unique token to store in DB using REST API
     */
    public void updateToken(String token) {
        loggedCustomer.setToken(token);
        Call<CustomerEntity> updateCall = apiAccountService.updateToken(loggedCustomer);
        updateCall.enqueue(new Callback<CustomerEntity>() {
            @Override
            public void onResponse(Call<CustomerEntity> call, Response<CustomerEntity> response) {
                Log.i(activity.getString(R.string.TAG_ACCOUNT_HANDLER), "Updated token successfully, lauching MainActivity");
                activity.startMainActivity(loggedCustomer);
            }

            @Override
            public void onFailure(Call<CustomerEntity> call, Throwable t) {
                Log.e(activity.getString(R.string.TAG_ACCOUNT_HANDLER), "Update token failure : " + t.getMessage() + " caused by " + t.getCause());
            }
        });
    }
}
