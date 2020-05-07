package com.pds.pgmapp.handlers;

import android.util.Log;

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

/**
 * AccountHandler : handles account management : connection and token update
 */
public class AccountHandler {
    Callback<CustomerEntity> updateTokenCallback;
    private AccountActivity activity;
    private AccountService apiAccountService;
    private CustomerEntity loggedCustomer;

    /**
     * Init retrofit consumer API
     *
     * @param a
     */
    public AccountHandler(AccountActivity a) {
        activity = a;
        apiAccountService = RetrofitInstance.getRetrofitInstance().create(AccountService.class);
    }

    /**
     * Use a given retrofit consumer API (mainly for test purpose)
     *
     * @param a
     * @param ap
     */
    public AccountHandler(AccountActivity a, AccountService ap) {
        activity = a;
        apiAccountService = ap;
    }

    public CustomerEntity getLoggedCustomer() {
        return this.loggedCustomer;
    }

    public void setLoggedCustomer(CustomerEntity customer) {
        this.loggedCustomer = customer;
    }

    public void setUpdateTokenCallback(Callback<CustomerEntity> callback) {
        this.updateTokenCallback = callback;
    }

    /**
     * Get heartbeat message from remote server to test connectivity
     *
     * @param callback Callback called when server respond
     * @return heartbeat message
     */
    public Call<ResponseBody> asyncHeartbeat(Callback<ResponseBody> callback) {
        Call<ResponseBody> heartbeatCall = apiAccountService.heartbeat();
        heartbeatCall.enqueue(callback);
        return heartbeatCall;
    }

    /**
     * Get heartbeat message from remote server to test connectivity
     *
     * @return heartbeat message
     * @throws IOException
     * @deprecated
     */
    @Deprecated
    public ResponseBody syncHeartbeat() throws IOException {
        Call<ResponseBody> heartbeatCall = apiAccountService.heartbeat();
        return heartbeatCall.execute().body();
    }

    /**
     * Connection function, call remote API and try to authenticate with given credentials
     *
     * @param loginCustomer customer to authenticate
     * @param callback      callback function called when requests' over
     * @return authenticated customer
     */
    public Call<CustomerEntity> asyncConnect(CustomerEntity loginCustomer, Callback<CustomerEntity> callback) {
        Call<CustomerEntity> connectCall = apiAccountService.connect(RequestBody.create(MediaType.parse("json"), "{ \"username\": \"" + loginCustomer.getUsername() + "\", \"password\": \"" + loginCustomer.getPassword() + "\" }"));
        connectCall.enqueue(callback);
        return connectCall;
    }

    /**
     * Connection function, call remote API and try to authenticate with given credentials. Deprecated on Android > 4.0
     *
     * @param username
     * @param password
     * @return true if connection succeed
     * @throws IOException
     * @deprecated
     */
    @Deprecated
    public boolean syncConnect(String username, String password) throws IOException {
        boolean ret;
        Call<CustomerEntity> connectCall = apiAccountService.connect(RequestBody.create(MediaType.parse("json"), "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }"));
        loggedCustomer = connectCall.execute().body();
        if (loggedCustomer != null && (loggedCustomer.getUsername() != null && loggedCustomer.getUsername().equals(username) && loggedCustomer.getPassword() != null && loggedCustomer.getPassword().equals(password))) {
            ret = true;
        } else {
            ret = false;
        }
        return ret;
    }

    /**
     * Update the user's Firebase Cloud Messaging Token
     *
     * @param token    FCM unique token to store in DB using REST API
     * @param callback Callback called when requests terminate
     */
    public void asyncUpdateToken(String token, Callback<CustomerEntity> callback) {
        loggedCustomer.setToken(token);
        Call<CustomerEntity> updateCall = apiAccountService.updateToken(loggedCustomer);
        updateCall.enqueue(callback);
    }

    /**
     * Update the user's Firebase Cloud Messaging Token. Deprecated on Android > 4.0
     *
     * @param token FCM unique token to store in DB using REST API
     * @return Updated customer entity
     * @throws IOException
     * @deprecated
     */
    @Deprecated
    public CustomerEntity syncUpdateToken(String token) throws IOException {
        loggedCustomer.setToken(token);
        Call<CustomerEntity> updateCall = apiAccountService.updateToken(loggedCustomer);
        return updateCall.execute().body();
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
                        this.asyncUpdateToken(token, updateTokenCallback);
                    }
                });
    }
}
