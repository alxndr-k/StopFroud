package com.hakaton.stopfraud.api;

import com.hakaton.stopfraud.api.data.Status;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface Resources {

    @GET("/point_status.json")
    void getPointStatus(@Query("long") double lon, @Query("lat") double lat, Callback<Status> callback);

    @GET("/point_status.json")
    void getPointStatus(Callback<Status> callback);
}
