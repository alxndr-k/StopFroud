package com.hakaton.stopfraud.api;

import com.hakaton.stopfraud.api.data.Point;
import com.hakaton.stopfraud.api.data.Status;
import com.hakaton.stopfraud.api.data.SubmitPoint;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface Resources {

    @GET("/point_status/long/{long}/lat/{lat}")
    void getPointStatus(@Path("long") double lon, @Path("lat") double lat, Callback<Status> callback);

    @GET("/points/long/{long}/lat/{lat}")
    void getPoints(@Path("long") double lon, @Path("lat") double lat, Callback<List<Point>> callback);

    @POST("/suggest_point")
    void submitPoint(@Body SubmitPoint point, Callback<Response> callback);
}
