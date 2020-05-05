package com.pds.pgmapp.retrofit;

import com.pds.pgmapp.model.CustomerEntity;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Account Service : Retrofit consumer
 */
public interface AccountService {

    @GET("account/")
    Call<ResponseBody> heartbeat();

    @Headers("Content-Type: application/json")
    @POST("account/connect/")
    Call<CustomerEntity> connect(@Body RequestBody customer);

    @POST("account/update/token")
    Call<CustomerEntity> updateToken(@Body CustomerEntity customer);

}
