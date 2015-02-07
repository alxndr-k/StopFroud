package com.hakaton.stopfraud.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hakaton.stopfraud.R;
import com.hakaton.stopfraud.api.Api;
import com.hakaton.stopfraud.api.data.Status;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ActionBarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_map);
        setUpMapIfNeeded();

        Api.getStatus(mStatusCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker")
                .snippet("Marker Hint"));

//        Geocoder gc = new Geocoder(MapActivity.this);
//        try {
//            List<Address> addrs = gc.getFromLocation(0, 0, 3);
//            for (Address addr : addrs)
//                Log.i("SMTH2", addr.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });
    }

    private Callback<Status> mStatusCallback = new Callback<Status>() {
        @Override
        public void success(Status status, Response response) {

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };
}
