package com.hakaton.stopfraud.api;

import com.hakaton.stopfraud.api.data.Point;
import com.hakaton.stopfraud.api.data.Status;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by felistrs on 07.02.15.
 */
public interface Resources {

    @GET("/point_status/long/{long}/lat/{lat}")
    void getPointStatus(@Path("long") double lon, @Path("lat") double lat, Callback<Status> callback);

    @GET("/point_status/long/{long}/lat/{lat}")
    void getPoints(@Path("long") double lon, @Path("lat") double lat, Callback<List<Point>> callback);
}
