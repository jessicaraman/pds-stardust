package com.pds.pgmapp.retrofit;

import com.pds.pgmapp.geolocation.LocationHistoryEntity;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LocationDataService {

    @POST("location/history")
    Call<LocationHistoryEntity> saveLocationHistory(@Body LocationHistoryEntity entity);

}
