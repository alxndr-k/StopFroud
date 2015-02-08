package com.hakaton.stopfraud.ui;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hakaton.stopfraud.App;
import com.hakaton.stopfraud.R;
import com.hakaton.stopfraud.api.Api;
import com.hakaton.stopfraud.api.data.Point;

import java.io.File;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private static final int REQUEST_SUBMIT_POINT = 1;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_map);

        findViewById(R.id.add).setOnClickListener(this);

        setUpMapIfNeeded();
        Api.getPoints(mPointsCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh) {
            Api.getPoints(mPointsCallback);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            startActivityForResult(SubmitActivity.newIntent(this, getCapturedFile().getAbsolutePath()), REQUEST_SUBMIT_POINT);
            overridePendingTransition(R.anim.slide_to_left_show_in, R.anim.slide_to_left_show_out);
        } else if (requestCode == REQUEST_SUBMIT_POINT) {
            Api.getPoints(mPointsCallback);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getCapturedFile()));
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            overridePendingTransition(R.anim.slide_to_left_show_in, R.anim.slide_to_left_show_out);
        }
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
        if (location == null) {
            return;
        }
        // TODO: remove return. Zoom to locations

        LatLng latLng = new LatLng(location.getLatitude(),
                location.getLongitude());

        mMap.addMarker(new MarkerOptions().position(
                latLng).title(this.getString(R.string.your_location)))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_maps_indicator_current_position));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.f));
    }

    private File getCapturedFile() {
        return new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "capture.jpg");
    }

    private Callback<List<Point>> mPointsCallback = new Callback<List<Point>>() {
        @Override
        public void success(List<Point> points, Response response) {
            for (Point point : points) {
                BitmapDescriptor bitmap = null;
                switch (point.state) {
                    case Point.STATE_IN_PROGRESS:
                        bitmap = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
                        break;
                    case Point.STATE_VERIFIED:
                        bitmap = BitmapDescriptorFactory.defaultMarker(
                                (BitmapDescriptorFactory.HUE_GREEN + BitmapDescriptorFactory.HUE_CYAN) / 2);
                        break;
                    case Point.STATE_FAKE:
                        bitmap = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                        break;
                }

                LatLng latLng = new LatLng(point.latitude, point.longitude);
                mMap.addMarker(new MarkerOptions().position(
                        latLng).title(point.name).snippet(point.description))
                        .setIcon(bitmap);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            App.showToast(R.string.main_failed);
        }
    };
}
