package com.ldxx.android.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ldxx.android.base.R;

/**
 * 渐变TextView
 * https://blog.upcurvelabs.com/how-to-create-a-gradient-textview-in-android-c21331da86ab#.1ry6f4brm
 */

public class GradientTextView extends TextView {
    private int startColor;
    private int endColor;

    public GradientTextView(Context context) {
        this(context,null);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView);
        startColor = a.getColor(R.styleable.GradientTextView_start_color, 0XFF000000);
        endColor = a.getColor(R.styleable.GradientTextView_end_color, 0XFF000000);
        a.recycle();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top,
                            int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //Setting the gradient if layout is changed
        if (changed) {
            getPaint().setShader(new LinearGradient(0,0,
                    getWidth(),getHeight(),
                    startColor,endColor,
                    Shader.TileMode.CLAMP));
        }
    }

}
