package com.hakaton.stopfraud.api.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by felistrs on 07.02.15.
 */
public class Status {
    private static final int NO = -1;
    private static final int UNKNOWN = 0;
    private static final int FRAUD = 1;
    private static final int VALID = 2;

    @SerializedName("status")
    private int mStatus = NO;

    public boolean isUnknown() {
        return UNKNOWN == mStatus;
    }

    public boolean isFraud() {
        return FRAUD == mStatus;
    }

    public boolean isValid() {
        return VALID == mStatus;
    }
}
