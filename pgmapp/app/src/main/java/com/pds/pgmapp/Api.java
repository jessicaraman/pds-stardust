package com.pds.pgmapp;

import com.pds.pgmapp.model.PathNode;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "https://simplifiedcoding.net/demos/";

    @GET("path")
    Call<PathNode> getNodes();
}