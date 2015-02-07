package com.hakaton.stopfroud;

import android.app.Application;

/**
 * Created by felistrs on 07.02.15.
 */
public class App extends Application {

    public static App self;

    @Override
    public void onCreate() {
        super.onCreate();

        self = this;
    }
}
