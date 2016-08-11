package com.ldxx.android.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import com.ldxx.android.base.R;


/**
 * 虚线控件
 */

public class DashedLine extends View {
    private Paint paint = null;
    private Path path = null;
    private PathEffect pe = null;

    public DashedLine(Context paramContext) {
        this(paramContext, null);
    }

    public DashedLine(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        TypedArray a = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.DashedLine);
        int lineColor = a.getColor(R.styleable.DashedLine_line_color, 0XFF000000);
        a.recycle();
        this. paint = new Paint();
        this. path = new Path();
        this. paint.setStyle(Paint.Style. STROKE);
        this. paint.setColor(lineColor);
        this. paint.setAntiAlias( true);
        this. paint.setStrokeWidth( dip2px(getContext(), 4.0F));
        float[] arrayOfFloat = new float[4];
        arrayOfFloat[0] =  dip2px(getContext(), 4.0F);
        arrayOfFloat[1] =  dip2px(getContext(), 4.0F);
        arrayOfFloat[2] =  dip2px(getContext(), 4.0F);
        arrayOfFloat[3] =  dip2px(getContext(), 4.0F);
        this. pe = new DashPathEffect(arrayOfFloat, dip2px(getContext(), 2.0F));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this. path.moveTo(0.0F, 0.0F);
        this. path.lineTo(getMeasuredWidth(), 0.0F);
        this. paint.setPathEffect( this. pe);
        canvas.drawPath( this. path, this. paint);
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
