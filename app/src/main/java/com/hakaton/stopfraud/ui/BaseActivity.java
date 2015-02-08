package com.hakaton.stopfraud.ui;

import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.hakaton.stopfraud.R;

/**
 * Created by felistrs on 08.02.15.
 */
public class BaseActivity extends ActionBarActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_to_left_hide_in, R.anim.slide_to_left_hide_out);
    }
}
