package com.hakaton.stopfraud.api.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by felistrs on 08.02.15.
 */
public class SubmitPoint {
    @SerializedName("lon")
    public double lon;

    @SerializedName("lat")
    public double lat;

    @SerializedName("name")
    public String name;

}
