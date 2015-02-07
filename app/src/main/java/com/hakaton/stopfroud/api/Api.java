package com.hakaton.stopfroud.api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by felistrs on 07.02.15.
 */
public interface Api {

    @GET("/point_status")
    Status getPointStatus(@Query("long") double lon, @Query("lat") double lat, Callback<Status> callback);

}
