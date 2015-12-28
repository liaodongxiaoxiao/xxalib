package com.ldxx.xxalib.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ldxx.xxalib.R;

public class CarViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_view);
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

        TextView tv= (TextView) findViewById(R.id.info);
        String details = "CardView继承于Framelayout，所以Framelayout的属性他都有，同时CardView还有几个特殊的属性：\n" +
                "elevation:CardView的Z轴阴影,在API21（Android L）。只能通过xml中的elevation属性指定；\n\n" +
                "cardBackgroundColor:CardView的卡片颜色,2.0以上，只能通过xml的cardBackgroundColor进行指定；\n\n" +
                "cardConerRadius:CardView卡片的四角圆角矩形程度，单位dimen（dp px sp），可以通过xml指定，也可以通过代码中的setRadius指定。";
        tv.setText(details);
    }

}
