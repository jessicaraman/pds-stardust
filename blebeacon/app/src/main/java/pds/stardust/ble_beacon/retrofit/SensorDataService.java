package pds.stardust.ble_beacon.retrofit;

import pds.stardust.ble_beacon.entity.SensorEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SensorDataService {

    @GET("sensor/{id}")
    Call<SensorEntity> getSensorById(@Path("id") String id);

}

