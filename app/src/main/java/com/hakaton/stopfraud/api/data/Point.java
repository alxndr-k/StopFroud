package com.hakaton.stopfraud.api.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by felistrs on 07.02.15.
 */
public class Point {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("long")
    private double mLongitude;

    @SerializedName("lat")
    private double mLatitude;
}
