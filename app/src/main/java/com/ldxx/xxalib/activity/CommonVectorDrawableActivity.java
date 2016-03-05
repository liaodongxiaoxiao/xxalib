package com.ldxx.xxalib.activity;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.ldxx.xxalib.R;

public class CommonVectorDrawableActivity extends AppCompatActivity {

    private ImageView heartImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_vector_drawable);
        heartImage = (ImageView) findViewById(R.id.heart_animate);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Drawable drawable = heartImage.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }
}
