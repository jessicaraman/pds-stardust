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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

        List<LocationEntity> allLocations = dbHandler.getAllLocations();

        Log.d("LOCATION WORKER", "TOTAL = " + allLocations.size());
        Log.d("dada", LocalDateTime.now().toString());

        Data outputData = new Data.Builder().putString("TOTAL = " + allLocations.size(), "Jobs Finished").build();

        // Send location history to backend

        dbHandler.truncateLocation();

        return Result.success(outputData);
    }
}
