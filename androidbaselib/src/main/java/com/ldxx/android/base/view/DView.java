package com.ldxx.android.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.ldxx.android.base.R;

/**
 * Created by LDXX on 2015/12/22.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class DView extends View {
    //private static final String TAG = "DView";
    private int textColor = Color.BLACK;
    private int lineColor = Color.RED;
    private String text;
    private int textSize = 14;
    private TextPaint textPaint;
    private Paint linePaint;
    public static final int OUT_LINE_WIDTH = 6;
    private float scale;

    private Rect mTextBounds;
    private PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);

    private Path path;


    public DView(Context context) {
        this(context, null);
    }

    public DView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context con, AttributeSet attrs, int defStyle) {
        scale = con.getResources().getDisplayMetrics().density;
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.DView, defStyle, 0);
        try {

            text = a.getString(R.styleable.DView_text);
            textSize = (int) a.getDimension(R.styleable.DView_textSize, textSize * scale);
            textColor = a.getColor(R.styleable.DView_textColor, textColor);
            lineColor = a.getColor(R.styleable.DView_lineColor, lineColor);

        } finally {
            a.recycle();
        }

        initPaints();
        initTextBound();

        invalidateTextPaintAndMeasurements();
        //resetColor();
    }

    private void initPaints() {
        textPaint = new TextPaint();
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);

        linePaint = new Paint();
        linePaint.setColor(lineColor);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);


        path = new Path();
    }

    private void initTextBound() {
        mTextBounds = new Rect();
        if (!TextUtils.isEmpty(text)) {
            textPaint.getTextBounds(text, 0, text.length(), mTextBounds);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int defaultSize = (int) (textSize + 10 * scale);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            //Log.e(TAG, "onMeasure: defaultSize");
            setMeasuredDimension(defaultSize, defaultSize);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            //Log.e(TAG, "onMeasure: heightSpecSize");
            setMeasuredDimension(defaultSize, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            //Log.e(TAG, "onMeasure: widthSpecSize");
            setMeasuredDimension(widthSpecSize, defaultSize);
        } else {
            //Log.e(TAG, "onMeasure: widthSpecSize: "+widthMeasureSpec+" heightSpecSize: " + heightSpecSize);
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        //canvas.drawColor(Color.GRAY);
        int height = getMeasuredHeight() - getPaddingBottom() - getPaddingTop() - OUT_LINE_WIDTH;
        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - OUT_LINE_WIDTH;

        int x = width / 2;
        int y = height / 2;

        int r = Math.min(x, y);
        //Log.e(TAG, "onDraw: " + r + " " + x + " " + y);
        linePaint.setStrokeWidth(OUT_LINE_WIDTH);
        canvas.drawRect(x - r + OUT_LINE_WIDTH / 2, y - r + OUT_LINE_WIDTH / 2, x + r, y + r, linePaint);

        linePaint.setPathEffect(effects);
        linePaint.setStrokeWidth(4);

        path.reset();
        //canvas.drawLine(x, y, x, y - height / 2, linePaint);
        path.moveTo(x, y);
        path.lineTo(x, y - r);
        canvas.drawPath(path, linePaint);

        // canvas.drawLine(x, y, x - width / 2, y - height / 2, linePaint);
        path.moveTo(x, y);
        path.lineTo(x - r, y - r);
        canvas.drawPath(path, linePaint);
        //canvas.drawLine(x, y, width, y - height / 2, linePaint);
        path.moveTo(x, y);
        path.lineTo(x + r, y - r);
        canvas.drawPath(path, linePaint);

        // canvas.drawLine(x, y, width, y, linePaint);
        path.moveTo(x, y);
        path.lineTo(x + r, y);
        canvas.drawPath(path, linePaint);

        //canvas.drawLine(x, y, width, height, linePaint);
        path.moveTo(x, y);
        path.lineTo(x + r, y + r);
        canvas.drawPath(path, linePaint);

        //canvas.drawLine(x, y, x, height, linePaint);
        path.moveTo(x, y);
        path.lineTo(x, y + r);
        canvas.drawPath(path, linePaint);

        //canvas.drawLine(x, y, x - width / 2, height, linePaint);
        path.moveTo(x, y);
        path.lineTo(x - r, y + r);
        canvas.drawPath(path, linePaint);

        //canvas.drawLine(x,y,x-width/2,y,linePaint);
        path.moveTo(x, y);
        path.lineTo(x - r, y);
        canvas.drawPath(path, linePaint);

        if (!TextUtils.isEmpty(text)) {
            float textBaseY = getMeasuredHeight() - (getMeasuredHeight() - mTextHeight) / 2 - textBottom;
            canvas.drawText(text, (x - mTextBounds.width() / 2) , textBaseY, textPaint);
        }
    }

    private float textBottom;
    /**
     * text height
     */
    private float mTextHeight;

    private void invalidateTextPaintAndMeasurements() {
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        textBottom = fontMetrics.bottom;
        mTextHeight = fontMetrics.bottom - fontMetrics.top;
    }

    public void setText(String text) {
        this.text = text;
        invalidateTextPaintAndMeasurements();
        invalidate();
    }

    public void setTextSize(int textSize){
        this.textSize =textSize;
        invalidateTextPaintAndMeasurements();
        invalidate();
    }
}
