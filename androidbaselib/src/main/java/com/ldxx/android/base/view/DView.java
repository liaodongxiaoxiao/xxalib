package com.ldxx.android.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by LDXX on 2015/12/22.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class DView extends TextView {
    public DView(Context context) {
        super(context);
    }

    public DView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
