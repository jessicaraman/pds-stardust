package com.pds.pgmapp.handlers;

import android.util.Log;
import android.widget.Toast;

import com.pds.pgmapp.R;
import com.pds.pgmapp.activity.AccountActivity;
import com.pds.pgmapp.model.CustomerEntity;
import com.pds.pgmapp.retrofit.AccountService;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * AcoountHandler Test
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountHandlerTest {
    @Mock
    AccountActivity accountActivity;
    private MockWebServer mockWebServer = new MockWebServer();
    private CustomerEntity customerEntity ;
    private AccountService apiService;
    private AccountHandler accountHandler;
    private CustomerEntity updatedEntity;

    @Before
    public void setup() throws IOException {
        mockWebServer.start();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
        apiService = new retrofit2.Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AccountService.class);
        accountHandler = new AccountHandler(null, apiService);
        customerEntity = new CustomerEntity(1, "name", "surname", "image", "username", "password", "notoken");
        updatedEntity = new CustomerEntity(1, "name", "surname", "image", "username", "password", "updated_token");
    }

    @After
    public void teardown() throws IOException {
        mockWebServer.shutdown();
    }

    // ****************
    // Heartbeat test :
    // ****************
    @Test
    public void asyncHeartbeatTest()  {
        // Mock REST API /heartbeat response
        MockResponse heartbeatResponse = new MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody("{\"msg\":\"Account API\" } ");
        mockWebServer.enqueue(heartbeatResponse);
        ResponseBody expectedBody = ResponseBody.create(MediaType.parse("text"), "{\"msg\":\"Account API\" } ") ;

        // Mock Callback
        // ***********
        Callback<ResponseBody> heartbeatTestCallback = new Callback< ResponseBody >() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Assert.assertEquals(expectedBody.string(), response);
                } catch (IOException e) {
                    Assert.fail();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Assert.fail();
            }
        };

        // Verify AccountHandler is making the right call
        accountHandler.asyncHeartbeat(heartbeatTestCallback);
    }
    @Test
    public void syncHeartbeatTest() throws IOException {
        // Mock REST API /heartbeat response
        MockResponse heartbeatResponse = new MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody("{\"msg\":\"Account API\" } ");
        mockWebServer.enqueue(heartbeatResponse);

        // Verify AccountHandler is making the right call
        ResponseBody expectedBody = ResponseBody.create(MediaType.parse("text"), "{\"msg\":\"Account API\" } ") ;
        Assert.assertEquals(expectedBody.string(), accountHandler.syncHeartbeat().string());
    }


    // **************
    // Connect test :
    // **************
    @Test
    public void asyncConnectTest()  {
        // Mock REST API /connect response
        MockResponse connectResponse = new MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(customerEntity.toString());
        mockWebServer.enqueue(connectResponse);

        Callback<CustomerEntity> connectTestCallback = new Callback<CustomerEntity>() {
            @Override
            public void onResponse(Call<CustomerEntity> call, Response<CustomerEntity> response) {
                    if (response.isSuccessful()) {
                        CustomerEntity loggedCustomer = response.body();
                        if (loggedCustomer != null && (loggedCustomer.getUsername() != null && loggedCustomer.getUsername().equals(customerEntity.getUsername()) && loggedCustomer.getPassword() != null && loggedCustomer.getPassword().equals(customerEntity.getPassword()))) {
                            Assert.assertEquals(customerEntity.toString(), loggedCustomer.toString());
                        } else {
                            Assert.fail();
                        }
                    } else {
                        Assert.fail();
                    }
            }

            @Override
            public void onFailure(Call<CustomerEntity> call, Throwable t) {
                Assert.fail();
            }
        };

        accountHandler.asyncConnect(customerEntity, connectTestCallback);
    }
    @Test
    public void syncConnectTest() throws IOException {
        // Mock REST API /connect response
        MockResponse connectResponse = new MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(customerEntity.toString());
        mockWebServer.enqueue(connectResponse);
        // Verify AccountHandler is making the right API call
        Assert.assertEquals(true, accountHandler.syncConnect("username","password"));
        Assert.assertNotEquals(null, accountHandler.getLoggedCustomer());
        Assert.assertEquals(customerEntity.toString(), accountHandler.getLoggedCustomer().toString());
    }


    // *******************
    // Update token test :
    // *******************
    @Test
    public void asyncUpdateTokenTest() {
        accountHandler.setLoggedCustomer(customerEntity);
        // Mock REST API /update/token response
        MockResponse connectResponse = new MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(updatedEntity.toString());
        mockWebServer.enqueue(connectResponse);
        String token = "updated_token" ;

        Callback<CustomerEntity> updateTokenCallback = new Callback<CustomerEntity>() {
            @Override
            public void onResponse(Call<CustomerEntity> call, Response<CustomerEntity> response) {
                CustomerEntity loggedCustomer = response.body();
                Assert.assertEquals(updatedEntity.toString(), loggedCustomer.toString());
                Assert.assertEquals(token, loggedCustomer.getToken());
                Assert.assertEquals(token, accountHandler.getLoggedCustomer().getToken());
            }

            @Override
            public void onFailure(Call<CustomerEntity> call, Throwable t) {
                Assert.fail();
            }
        };
        accountHandler.setLoggedCustomer(customerEntity);
        accountHandler.asyncUpdateToken(token, updateTokenCallback);
    }
    @Test
    public void syncUpdateTokenTest() throws IOException {
        accountHandler.setLoggedCustomer(customerEntity);
        // Mock REST API /update/token response
        MockResponse connectResponse = new MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(updatedEntity.toString());
        mockWebServer.enqueue(connectResponse);
        // Verify AccountHandler is returning the right updated user entity
        Assert.assertEquals(updatedEntity.toString(), accountHandler.syncUpdateToken("updated_token").toString());
        Assert.assertEquals(updatedEntity.toString(), accountHandler.getLoggedCustomer().toString());
    }

}