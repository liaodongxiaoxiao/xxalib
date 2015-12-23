package com.ldxx.xxalib.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.ldxx.android.base.view.XXBallViewLayout;
import com.ldxx.android.base.view.XXBallView;
import com.ldxx.xxalib.R;

public class CustomXXBallViewActivity extends AppCompatActivity {
    private XXBallView ballView;
    private XXBallView nullBall;
    private XXBallViewLayout ball_layout;
    private XXBallViewLayout add_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_xxball_view);
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

        ballView = (XXBallView) findViewById(R.id.ball);
        ballView.setBallViewSelectedListener(new XXBallView.BallViewSelectedListener() {
            @Override
            public void OnBallViewSelected(XXBallView view, boolean isSelected) {
                Toast.makeText(CustomXXBallViewActivity.this, ballView.getText() + "号球" +
                        (isSelected ? "被选中" : "被取消选中"), Toast.LENGTH_SHORT).show();
            }
        });


        nullBall = (XXBallView) findViewById(R.id.ball_null);
        ball_layout = (XXBallViewLayout) findViewById(R.id.ball_layout);
        add_layout =  (XXBallViewLayout) findViewById(R.id.add_layout);
        add_layout.addBallView("07", Color.RED,true);
    }

    public void getSelectedBalls(View view) {
        Toast.makeText(CustomXXBallViewActivity.this,
                "选中：" + ball_layout.getSelectedOrderBalls(false),
                Toast.LENGTH_SHORT).show();
    }

    public void addBall(View view) {

    }
}
