package com.hakaton.stopfroud.api;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.hakaton.stopfroud.App;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by felistrs on 07.02.15.
 */
public class Validator {

    private RestAdapter mAdapter = new RestAdapter.Builder().setEndpoint("com.hakaton.stopfraud").build();
    private Api mApi = mAdapter.create(Api.class);

    public void getStatus(Callback<Status> callback) {
        getStatus(getLocation(), callback);
    }

    public void getStatus(Location l, Callback<Status> callback) {
        mApi.getPointStatus(l.getLongitude(), l.getLatitude(), callback);
    }

    // TODO: we need current location, not last known
    private Location getLocation() {
        LocationManager lm = (LocationManager) App.self.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);

        for (int i = providers.size() - 1; i >= 0; i--) {
            Location l = lm.getLastKnownLocation(providers.get(i));
            if (l != null) {
                return l;
            }
        }

        return null;
    }
}
