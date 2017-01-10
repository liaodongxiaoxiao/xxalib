package com.ldxx.xxalib.activity;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ldxx.xxalib.R;

public class CommonVectorDrawableActivity extends AppCompatActivity {
    private static final String TAG = "CommonVectorDrawableAct";
    private ImageView heartImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_vector_drawable);
        heartImage = (ImageView) findViewById(R.id.heart_animate);
        heartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable = heartImage.getDrawable();
                //Log.e(TAG, "onResume: drawable is Animatable"+(drawable instanceof Animatable) );
                if (drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Drawable drawable = heartImage.getDrawable();
        Log.e(TAG, "onResume: drawable is Animatable"+(drawable instanceof Animatable) );
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }
}
