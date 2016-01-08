package com.ldxx.android.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.ldxx.android.base.R;

/**
 * Created by LDXX on 2016/1/7.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class XXBatteryView extends View {
    public static final int LEFT = 0x001;
    public static final int RIGHT = 0x002;
    public static final int UP = 0x003;
    public static final int DOWN = 0x004;

    private int currentDirection = LEFT;

    private int normalColor = Color.BLUE;
    private int warnColor = Color.RED;
    private int goneColor = Color.GRAY;

    private Paint batteryPaint;
    private RectF base;
    private RectF head;

    public XXBatteryView(Context context) {
        this(context, null, 0);
    }

    public XXBatteryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XXBatteryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initAttrs(Context con, AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.XXBatteryView, defStyle, 0);
        try {
            currentDirection = a.getInt(R.styleable.XXBatteryView_direction, LEFT);
            //scale = con.getResources().getDisplayMetrics().density;

        } finally {
            a.recycle();
        }
    }

    private void initPaint() {
        batteryPaint = new Paint();
        batteryPaint.setColor(normalColor);
        batteryPaint.setStyle(Paint.Style.FILL);
        batteryPaint.setAntiAlias(true);

        base = new RectF();
        head = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        makeRectF();
        canvas.drawRoundRect(head, 3, 3, batteryPaint);
        canvas.drawRoundRect(base, 6, 6, batteryPaint);
    }

    private void makeRectF() {

        int left = getPaddingLeft();
        int top = getPaddingTop();
        int width;
        int height;
        width = getMeasuredWidth() - left - getPaddingRight() - 5;
        height = getMeasuredHeight() - getPaddingBottom() - top - 5;
        int headWidth;
        int headHeight;
        //左右
        if (currentDirection == LEFT || currentDirection == RIGHT) {

            headWidth = width / 8;
            headHeight = height / 2;
            //左
            if (currentDirection == LEFT) {
                head.set(left , (top + height / 2) - headHeight / 2, left  + headWidth, (top + height / 2) + headHeight / 2);
                base.set(left-10 + headWidth, top, left-10 + headWidth + width, top + height);
            } else {
                head.set(left + width - 3, (top + height / 2) - headHeight / 2, left + width - 3 + headWidth, (top + height / 2) + headHeight / 2);
                base.set(left, top, left + width, top + height);
            }

        } else {//上下

            headWidth = width / 2;
            headHeight = height / 8;
            if (currentDirection == UP) {
                head.set(left + width / 2 - headWidth / 2, top, left + width / 2 + headWidth / 2, top  + headHeight);
                base.set(left, top + headHeight-3, left + width, top + height + headHeight-3);
            } else {
                head.set(left + width / 2 - headWidth / 2, top + height - 3, left + width / 2 + headWidth / 2, top + headHeight + height - 3);
                base.set(left, top, left + width, top + height);
            }
        }
    }
}
