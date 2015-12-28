package com.ldxx.android.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ldxx.android.base.R;
import com.ldxx.android.base.utils.CommonUtils;
import com.ldxx.android.base.utils.XXDensityUtils;

/**
 * Created by LDXX on 2015/12/22.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class DLayout extends ViewGroup {
    private static final String TAG = "DLayout";
    @ColorInt
    private int backgroudColor;
    private int textSize;
    private String text;
    private int columnSpace = 5;
    private int rowSpace = 5;
    private int column;

    private Context context;

    public DLayout(Context context) {
        this(context, null);
    }

    public DLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }



    private int scale;

    private void init(Context context, AttributeSet attrs, int defStyle) {
        this.context = context;
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.DLayout, defStyle, 0);
        scale = (int) context.getResources().getDisplayMetrics().density;
        try {
            backgroudColor = a.getColor(R.styleable.DLayout_backgroudColor, -1);

            textSize = (int) a.getDimension(R.styleable.DLayout_textSize,
                    XXDensityUtils.sp2px(context, 14));

            column = a.getInt(R.styleable.DLayout_column, 4);
            text = a.getString(R.styleable.DLayout_text);

            float c = a.getDimension(R.styleable.DLayout_columnSpace, 0);
            if (c > 0) {
                columnSpace = (int) (c / scale);
            }

            float r = a.getDimension(R.styleable.XXBallViewLayout_rowSpace, 0);
            if (r > 0) {
                rowSpace = (int) (r / scale);
            }

        } finally {
            a.recycle();
        }

        if (!TextUtils.isEmpty(text)) {
            fillView();
        }
    }

    private void fillView() {
        String[] str = text.split(",");
        //TextView tv;
        DView tv;
        for (String s : str) {
            //tv = new TextView(context);
            tv = new DView(context);
            tv.setTextSize(textSize);
            tv.setText(s);
            LinearLayout.LayoutParams pa = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            tv.setLayoutParams(pa);
            if (backgroudColor == -1) {
                tv.setBackgroundColor(Color.parseColor(CommonUtils.getRandColorCode()));
            } else {
                tv.setBackgroundColor(backgroudColor);
            }

            //tv.setTextColor(Color.WHITE);
            tv.setOnClickListener(listener);
            this.addView(tv);
        }
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);

        int measuredWidth;
        int measuredHeight;
        final int childCount = getChildCount();
        measureChildren(widthSpec, heightSpec);

        int widthSpecSize = MeasureSpec.getSize(widthSpec);
        int widthSpecMode = MeasureSpec.getMode(widthSpec);
        int heightSpecSize = MeasureSpec.getSize(heightSpec);
        int heightSpecMode = MeasureSpec.getMode(heightSpec);
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth() * column + columnSpace * (column - 1);
            measuredHeight = childView.getMeasuredHeight() * getRowCount() + rowSpace * (getRowCount() - 1);
            setMeasuredDimension(measuredWidth, measuredHeight);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measuredHeight = childView.getMeasuredHeight() * getRowCount() + rowSpace * (getRowCount() - 1);
            setMeasuredDimension(widthSpecSize, measuredHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth() * column + columnSpace * (column - 1);
            setMeasuredDimension(measuredWidth, heightSpecSize);
        } else {
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        }

    }

    private int getRowCount() {
        return (int) Math.ceil(getChildCount() * 1d / column);
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int childWidth;
        int childHeight;

        childWidth = (width - getPaddingLeft() - getPaddingRight() - (column - 1) * columnSpace) / column;
        childHeight = (height - getPaddingTop() - getPaddingBottom() - (getRowCount() - 1) * rowSpace) / getRowCount();

        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            //不是gone就得占位
            if (child.getVisibility() != GONE) {
                //child.layout(childLeft, childTop, childWidth, childHeight);
                child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
                //Log.e(TAG, i + " onLayout: " + childLeft + " " + childTop);
                if ((i + 1) % column == 0) {
                    childLeft = getPaddingLeft();
                    childTop += childHeight + rowSpace;
                } else {
                    childLeft += childWidth + columnSpace;
                }

                //Log.e(TAG, "onLayout i:" + i + " childLeft:" + childLeft + " childTop:" + childTop);
            }

        }
    }

    OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context, ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
        }
    };
}
