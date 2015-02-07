package com.hakaton.stopfraud.ui;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hakaton.stopfraud.R;
import com.hakaton.stopfraud.api.Api;
import com.hakaton.stopfraud.api.data.Status;

import java.io.IOException;
import java.util.List;

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
        Location location = Api.getLocation();
        if (location == null)
            return;
        // TODO:

        mMap.addMarker(new MarkerOptions().position(
                new LatLng(location.getLatitude(),
                           location.getLongitude())).title("Marker")
                .snippet("Marker Hint"))
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        Geocoder gc = new Geocoder(MainActivity.this);
        try {
            List<Address> addrs = gc.getFromLocation(0, 0, 3);
            for (Address addr : addrs)
                Log.i("SMTH2", addr.toString());
        } catch (IOException e) {
            Log.i("SMTH2", "Error");
            e.printStackTrace();
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });

//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
//                Constants.MAP_ZOOM));
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
