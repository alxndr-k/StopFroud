package com.hakaton.stopfraud.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.hakaton.stopfraud.R;
import com.hakaton.stopfraud.api.Api;
import com.hakaton.stopfraud.api.data.Status;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_scan);

        Api.getStatus(mStatusCallback);
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
