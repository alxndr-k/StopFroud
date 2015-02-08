package com.hakaton.stopfraud.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hakaton.stopfraud.R;

/**
 * Created by felistrs on 08.02.15.
 */
public class MapFragment extends Fragment {
    private  static  MapFragment mInstance = null;

    public static MapFragment getInstance() {
        if (mInstance == null)
            mInstance = new MapFragment();
        return mInstance;
    }

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f_map, container, false);
        return rootView;
    }

    public static String getTitle()
    {
        return "Map";
    }
}
