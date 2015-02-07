package com.hakaton.stopfraud.api;

import com.hakaton.stopfraud.api.data.Status;
import com.hakaton.stopfraud.api.data.Token;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by felistrs on 07.02.15.
 */
public interface Resources {

    @GET("/point_status")
    void getPointStatus(@Query("long") double lon, @Query("lat") double lat, Callback<Status> callback);

    @GET("/get_token")
    void getToken(Callback<Token> callback);
}
