package com.ldxx.xxalib.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ldxx.xxalib.R;

public class TextFontsActivity extends AppCompatActivity {
    private TextView tv1;
    private TextView tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_fonts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        tv1 = (TextView) findViewById(R.id.fonts_text1);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Android.ttf");
        tv1.setTypeface(face);

        tv3 = (TextView) findViewById(R.id.fonts_text3);
        face = Typeface.createFromAsset(getAssets(),
                "fonts/fangzhengliuxingtijianti.ttf");
        tv3.setTypeface(face);
    }

}
