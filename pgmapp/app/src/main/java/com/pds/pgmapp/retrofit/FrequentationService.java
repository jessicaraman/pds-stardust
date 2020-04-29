package com.pds.pgmapp.retrofit;

import com.pds.pgmapp.model.PassageEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FrequentationService {

    //@Headers("Content-Type: application/json")
    @POST("frequentation/addData")
    Call<PassageEntity> postFrequentationData(@Body PassageEntity entity);

}
