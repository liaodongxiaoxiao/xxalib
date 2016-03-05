package com.ldxx.xxalib.activity;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.ldxx.xxalib.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CaseWindowManagerActivity extends AppCompatActivity {
    private WindowManager mManager;
    private WindowManager.LayoutParams params;
    private View notificationOne;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_window_manager);
        ButterKnife.bind(this);

        mManager = (WindowManager) getSystemService(
                Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();

        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        int flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        // | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        params.flags = flags;
        params.format = PixelFormat.RGBA_8888;
        //params.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明

        mInflater = LayoutInflater.from(this);
    }

    @OnClick({R.id.notification_one, R.id.notification_two})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.notification_one:
                notificationOne = mInflater.inflate(R.layout.notification_one, null, false);

                mManager.addView(notificationOne, params);

                notificationOne.setOnKeyListener(new View.OnKeyListener() {

                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_BACK:
                                clearNotification();
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                break;
            case R.id.notification_two:
                break;
        }
    }

    private void clearNotification() {
        if (notificationOne != null) {
            mManager.removeView(notificationOne);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        clearNotification();
    }

}
