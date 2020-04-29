package com.pds.pgmapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pds.pgmapp.R;
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

public class AccountActivity extends AppCompatActivity {
    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    CustomerEntity loggedCustomer ;
    private static final String TAG = "AccountActivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        this.initWidgets();
        this.loginButton.setOnClickListener(v -> login());

    }

    private void login() {
        String username = this.usernameEditText.getText().toString().trim();
        String password = this.passwordEditText.getText().toString().trim();
        log("username = '"+ username + "' |password = '" + password + "'", false);

        if (username != null && !username.trim().equals("") && password != null && !password.trim().equals("")) {
            log("Login and password correctly specified !");
            this.connect(username, password);
        } else {
            log("You must specify username and password !");
        }
    }

    private void connect(String username, String password) {
        AccountService apiAccountService = RetrofitInstance.getRetrofitInstance().create(AccountService.class);
        CustomerEntity loginCustomer = new CustomerEntity() ;
        loginCustomer.setUsername(username);
        loginCustomer.setPassword(password);
        Call<ResponseBody> heartbeatCall = apiAccountService.heartbeat();

        Call<CustomerEntity> connectCall = apiAccountService.connect(RequestBody.create(MediaType.parse("json"), "{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }"));

        heartbeatCall.enqueue(((new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                log("heartbeat response = " + response.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                log("exception while network heartbeat :" + t.getMessage() + " caused by " + t.getCause());
            }
        })));

        connectCall.enqueue((new Callback<CustomerEntity>() {
            @Override
            public void onResponse(Call<CustomerEntity> call, Response<CustomerEntity> response) {
                try {
                    log("onResponse");
                    if (response.isSuccessful()) {
                        log("response.isSuccessful()");
                        loggedCustomer = response.body();
                        if (loggedCustomer != null && (loggedCustomer.getUsername().equals(username) && loggedCustomer.getPassword().equals(password)))
                        {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else
                        {
                            log("Authentication failed !");
                        }
                    } else {
                        String errorMessage = response.errorBody().string();
                        log("ERROR = " + errorMessage);
                    }
                } catch (IOException ex) {
                    log(ex.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CustomerEntity> call, Throwable t) {
               log("network failure : " + t.getMessage() + " caused by " + t.getCause());
               log("requested url was : " + call.request().url());
               log("request body was : " + call.request().toString());
            }
        }));
    }

    private void initWidgets() {
        this.loginButton = findViewById(R.id.loginButton);
        this.usernameEditText = findViewById(R.id.usernameEditText);
        this.passwordEditText = findViewById(R.id.passwordEditText);
    }

    public void log(String msg) {
        this.log(msg, true);
    }
    public void log(String msg, boolean toast) {
        Log.e(TAG, msg);
        if(toast) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        }
    }
}
