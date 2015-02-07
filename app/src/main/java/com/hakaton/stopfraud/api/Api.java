package com.hakaton.stopfraud.api;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.hakaton.stopfraud.App;
import com.hakaton.stopfraud.BuildConfig;
import com.hakaton.stopfraud.api.data.Status;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by felistrs on 07.02.15.
 */
public class Api {

    public static final String TAG = App.TAG + "/" + Api.class.getSimpleName();

    private static Resources api;

    static {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(2, TimeUnit.MINUTES);

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setClient(new OkClient(okHttpClient))
                .setEndpoint("http://inprogress.com.ua/api/avia-ws");

        if (BuildConfig.DEBUG) {
            builder.setLog(new RestLogger());
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }

        RestAdapter adapter = builder.build();
        api = adapter.create(Resources.class);
    }

    public static void getStatus(Callback<Status> callback) {
        getStatus(getLocation(), callback);
    }

    public static void getStatus(Location l, Callback<Status> callback) {
        api.getPointStatus(l.getLongitude(), l.getLatitude(), callback);
    }

    // TODO: we need current location, not last known
    private static Location getLocation() {
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

    private static class RestLogger implements RestAdapter.Log {
        @Override
        public void log(String s) {
            Log.d(TAG, s);
        }
    }
}
