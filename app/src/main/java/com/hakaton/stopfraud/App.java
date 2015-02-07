package com.hakaton.stopfraud;

import android.app.Application;

/**
 * Created by felistrs on 07.02.15.
 */
public class App extends Application {

    public static final String TAG = App.class.getSimpleName();
    public static App self;

    @Override
    public void onCreate() {
        super.onCreate();

        self = this;
    }
}
