package com.hakaton.stopfraud.api.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by felistrs on 07.02.15.
 */
public class Point {
    public static final int STATE_IN_PROGRESS = 0;
    public static final int STATE_VERIFIED = 1;
    public static final int STATE_FAKE = 2;

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("long")
    public double longitude;

    @SerializedName("lat")
    public double latitude;

    @SerializedName("state")
    public int state;
}
