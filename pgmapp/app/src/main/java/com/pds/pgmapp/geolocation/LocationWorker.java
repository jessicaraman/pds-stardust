package com.pds.pgmapp.geolocation;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.pds.pgmapp.handlers.DBHandler;
import com.pds.pgmapp.retrofit.LocationDataService;
import com.pds.pgmapp.retrofit.RetrofitInstance;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LocationWorker extends Worker {

    private DBHandler dbHandler;
    private Context context;

    public LocationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        this.context = context;
        dbHandler = DBHandler.getInstance(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Result doWork() {

        List<LocationEntity> locationHistory = dbHandler.getAllLocations();

        LocationHistoryEntity locationHistoryEntity = new LocationHistoryEntity();

        locationHistoryEntity.setId("1");
        locationHistoryEntity.setLocations(locationHistory);

        LocationDataService locationDataService = RetrofitInstance.getRetrofitInstance().create(LocationDataService.class);

        Call<LocationHistoryEntity> call = locationDataService.saveLocationHistory(locationHistoryEntity);

        call.enqueue(new Callback<LocationHistoryEntity>() {
            @Override
            public void onResponse(Call<LocationHistoryEntity> call, Response<LocationHistoryEntity> response) {

                Log.d("RETROFIT", "CALLBACK");
                dbHandler.truncateLocation();
            }

            @Override
            public void onFailure(Call<LocationHistoryEntity> call, Throwable t) {

                Log.e("ERROR", "");

            }
        });

        dbHandler.truncateLocation();

        Log.d("LOCATION WORKER", "TOTAL = " + locationHistory.size());
        //Log.d("dada", LocalDateTime.now().toString());

        Data outputData = new Data.Builder().putString("TOTAL = " + locationHistory.size(), "Jobs Finished").build();

        return Result.success(outputData);
    }

}
