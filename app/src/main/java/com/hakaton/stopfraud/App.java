package com.hakaton.stopfraud;

import android.app.Application;
import android.widget.Toast;

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

    public static void showToast(int message) {
        showToast(self.getString(message));
    }

    public static void showToast(String message) {
        Toast.makeText(self, message, Toast.LENGTH_LONG).show();
    }
}
