package com.hakaton.stopfraud.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hakaton.stopfraud.R;

import java.io.IOException;

/**
 * Created by felistrs on 08.02.15.
 */
public class SubmitActivity extends BaseActivity implements View.OnClickListener {

    public static Intent newIntent(Context context, String image) {
        Intent intent = new Intent(context, SubmitActivity.class);
        intent.putExtra("image_uri", image);
        return intent;
    }

    private View mSubmit, mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.a_submit);
        mSubmit = findViewById(R.id.submit);
        mSubmit.setOnClickListener(this);
        mProgress = findViewById(R.id.progress);

        ImageView image = (ImageView) findViewById(R.id.image);
        image.setImageBitmap(getImage(getIntent().getStringExtra("image_uri"), image.getWidth(), image.getHeight()));
    }

    @Override
    public void onClick(View v) {
        mProgress.setVisibility(View.VISIBLE);
        mSubmit.setVisibility(View.INVISIBLE);
    }

    public static Bitmap getImage(String uri, int w, int h) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 2;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        Bitmap bitmap = BitmapFactory.decodeFile(uri, options);
        try {
            ExifInterface exif = new ExifInterface(uri);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            return getRotated(bitmap, orientation);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private static Bitmap getRotated(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }

        Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return bmRotated;
    }
}
