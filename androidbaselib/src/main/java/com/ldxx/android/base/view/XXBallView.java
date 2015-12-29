package com.ldxx.android.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ldxx.android.base.R;

/**
 * 自定义圆形view
 */
public class XXBallView extends View {
    private static final String TAG = "CircleView";
    /**
     * circle text
     */
    private String mText;
    /**
     * circle fill color ,default red
     */
    private int mCircleColor = Color.RED;
    /**
     * text size default 16
     */
    private float mTextSize = 16;

    /**
     * text paint
     */
    private TextPaint mTextPaint;
    /**
     * text bound
     */
    private Rect mTextBounds;
    /**
     * text bottom
     */
    private float textBottom;
    /**
     * text height
     */
    private float mTextHeight;

    /**
     * paint for circle
     */
    private Paint mCirclePaint;
    private float scale;

    /**
     * ball is selectable
     */
    private boolean isSelectable = true;

    /**
     * ball select event listener
     */
    private BallViewSelectedListener ballViewSelectedListener;

    /**
     *
     */
    private boolean isSelected = false;

    public XXBallView(Context context) {
        this(context, null);
    }

    public XXBallView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XXBallView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context con, AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.XXBallView, defStyle, 0);
        try {

            mText = a.getString(R.styleable.XXBallView_text);
            scale = con.getResources().getDisplayMetrics().density;
            mTextSize = a.getDimension(R.styleable.XXBallView_textSize, mTextSize * scale);

            mCircleColor = a.getColor(R.styleable.XXBallView_ballColor, mCircleColor);

            isSelectable = a.getBoolean(R.styleable.XXBallView_selectable, false);
            isSelected = a.getBoolean(R.styleable.XXBallView_checked, false);
        } finally {
            a.recycle();
        }

        initPaints();
        initTextBound();

        invalidateTextPaintAndMeasurements();
        resetColor();
    }

    /**
     * init paints
     */
    private void initPaints() {
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setStrokeWidth(2);
        mCirclePaint.setAntiAlias(true);
    }

    private void initTextBound() {
        mTextBounds = new Rect();
        if (!TextUtils.isEmpty(mText)) {
            mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBounds);
        }
    }

    private void invalidateTextPaintAndMeasurements() {
        //mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(mTextSize);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        textBottom = fontMetrics.bottom;
        mTextHeight = fontMetrics.bottom - fontMetrics.top;
    }

    private int radius;

    public void setRadius(int radius) {
        this.radius = radius - 4;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawColor(Color.YELLOW);


        int contentWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int contentHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int x = contentWidth / 2;
        int y = contentHeight / 2;
        if (radius <= 0) {
            radius = (Math.min(contentHeight, contentWidth) / 2) - 4;
        }

        // Draw the circle
        canvas.drawCircle(x, y, radius, mCirclePaint);

        //draw text if text dose not null
        if (!TextUtils.isEmpty(mText)) {
            /*Paint rectPaint = new Paint();
            rectPaint.setColor(Color.YELLOW);
            Rect rect = new Rect();
            rect.bottom = y + mTextBounds.height() / 2;
            rect.top = y - mTextBounds.height() / 2;
            rect.left = x - mTextBounds.width() / 2;
            rect.right = x + mTextBounds.width() / 2;
            canvas.drawRect(rect, rectPaint);*/

            // Draw the text.
            float textBaseY = getMeasuredHeight() - (getMeasuredHeight() - mTextHeight) / 2 - textBottom;
            canvas.drawText(mText, x, textBaseY, mTextPaint);
        }

        /*Paint linePaint = new Paint();
        linePaint.setColor(Color.WHITE);
        canvas.drawLine(x, y, x, y - radius, linePaint);
        canvas.drawLine(x, y, x, y + radius, linePaint);
        canvas.drawLine(x, y, x - radius, y, linePaint);
        canvas.drawLine(x, y, x + radius, y, linePaint);*/


    }

    int defaultD = 100;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            defaultD = (int) (Math.min((right - left), (bottom - top)) * 1.4);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultD, defaultD);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultD, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, defaultD);
        } else {
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        }
    }

    /**
     * get text
     *
     * @return text in circle view
     */
    public String getText() {
        return mText;
    }

    /**
     * set text ball view
     *
     * @param text text in ball view center
     */
    public void setText(String text) {
        mText = text;
        invalidateTextPaintAndMeasurements();
        initTextBound();
        invalidate();
    }


    /**
     * get ball view filling color
     *
     * @return filling color
     */
    public int getBallColor() {
        return mCircleColor;
    }

    /**
     * set ball view checked or not
     *
     * @param isChecked checked or not
     */
    public void setChecked(boolean isChecked) {
        isSelected = isChecked;
        resetColor();
        invalidate();
    }

    public void setIsSelectable(boolean isSelectable) {
        this.isSelectable = isSelectable;
    }

    /**
     * set ball view filling color
     *
     * @param ballColor filling color
     */
    public void setBallColor(@ColorInt int ballColor) {
        this.mCircleColor = ballColor;
        resetColor();
        invalidate();
    }

    /**
     * ball view selected listener interface
     */
    public interface BallViewSelectedListener {
        /**
         * call this method when ball view selected status change
         *
         * @param view       current ball view
         * @param isSelected current ball view selected status
         */
        void OnBallViewSelected(XXBallView view, boolean isSelected);
    }

    /**
     * override touch method to catch ball view selected.
     *
     * @param event motion event
     * @return true or false
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isSelectable) {
                    isSelected = !isSelected;
                    resetColor();
                    invalidate();
                    if (ballViewSelectedListener != null) {
                        ballViewSelectedListener.OnBallViewSelected(this, isSelected);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * set text size
     *
     * @param textSize size of text
     */
    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
        invalidateTextPaintAndMeasurements();
        invalidate();
    }

    /**
     * reset color based on selected status.
     */
    private void resetColor() {
        if (isSelected) {
            mTextPaint.setColor(Color.WHITE);
            mCirclePaint.setStyle(Paint.Style.FILL);
        } else {
            mCirclePaint.setStyle(Paint.Style.STROKE);
            mCirclePaint.setStrokeWidth(4f);
            mTextPaint.setColor(mCircleColor);
        }
        mCirclePaint.setColor(mCircleColor);
    }

    /**
     * set ball view selected listener
     *
     * @param ballViewSelectedListener listener
     */
    public void setBallViewSelectedListener(BallViewSelectedListener ballViewSelectedListener) {
        this.ballViewSelectedListener = ballViewSelectedListener;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }
}
