package com.pds.pgmapp.retrofit;

import com.pds.pgmapp.sensor.SensorLabelResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SensorDataService {
    @GET("sensor/sensor/topic/{id}/label")
    Call<SensorLabelResponse> getLabelByTopicId(@Path("id") String id);
}
