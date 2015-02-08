package com.hakaton.stopfraud.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.hakaton.stopfraud.App;
import com.hakaton.stopfraud.R;
import com.hakaton.stopfraud.api.Api;

import java.io.IOException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
    private EditText mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.a_submit);
        mSubmit = findViewById(R.id.submit);
        mSubmit.setOnClickListener(this);
        mProgress = findViewById(R.id.progress);
        mName = (EditText) findViewById(R.id.name);
        mName.addTextChangedListener(mTextWatcher);
        mName.setSelected(false);

        ImageView image = (ImageView) findViewById(R.id.image);
        image.setImageBitmap(getImage(getIntent().getStringExtra("image_uri"), image.getWidth(), image.getHeight()));
    }

    @Override
    public void onClick(View v) {
        mProgress.setVisibility(View.VISIBLE);
        mSubmit.setVisibility(View.INVISIBLE);
        Api.submitPoint(mName.getText().toString(), new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                App.showToast(R.string.submit_successful);
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                App.showToast(R.string.submit_failed);
                finish();
            }
        });
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

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mSubmit.setSelected(s.length() > 0);
        }
    };
}
