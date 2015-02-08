package com.hakaton.stopfraud.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.hakaton.stopfraud.R;

/**
 * Created by felistrs on 08.02.15.
 */
public class AddPointActivity extends Activity {

    public static Intent newIntent(Context context, String imageUrl) {
        Intent intent = new Intent(context, AddPointActivity.class);
        intent.putExtra("image_uri", imageUrl);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.a_add_point);

        ImageView image = (ImageView) findViewById(R.id.image);
        image.setImageURI(Uri.parse(getIntent().getStringExtra("image_uri")));
    }
}
