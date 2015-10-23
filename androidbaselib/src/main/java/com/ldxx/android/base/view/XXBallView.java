package com.ldxx.android.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ldxx.android.base.R;
import com.ldxx.android.base.utils.XXDensityUtils;


/**
 * Balls
 */
public class XXBallView extends View {

    /**
     * context
     */
    private Context context;

    /**
     * ball num
     */
    private String num;

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

    /**
     *
     */
    private int ballColor = Color.RED;

    private int textSize;


    private TextPaint mTextPaint;
    private Paint mBallPaint;

    private Rect mTextBounds;

    private float radius;
    private float x;
    private float y;

    public XXBallView(Context context) {
        super(context);
        this.context = context;
        init(null, 0);
    }

    public XXBallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs, 0);
    }

    public XXBallView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.XXBallView, defStyle, 0);

        num = a.getString(
                R.styleable.XXBallView_num);
        ballColor = a.getColor(R.styleable.XXBallView_ball_color, Color.RED);

        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        textSize = (int) a.getDimension(R.styleable.XXBallView_text_size,
                XXDensityUtils.sp2px(context, 14));

        isSelectable = a.getBoolean(R.styleable.XXBallView_selectable, false);
        isSelected = a.getBoolean(R.styleable.XXBallView_checked, false);


        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        if (!TextUtils.isEmpty(num)) {
            initTextBound();
        }


        mBallPaint = new Paint();
        mBallPaint.setColor(ballColor);
        mBallPaint.setAntiAlias(true);


        resetColor();
        // Update TextPaint and text measurements from attributes
        //invalidateTextPaintAndMeasurements();
    }

    private void initTextBound() {
        mTextBounds = new Rect();
        mTextPaint.getTextBounds(num, 0, num.length(), mTextBounds);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        radius = Math.min(getMeasuredHeight() - getPaddingBottom() - getPaddingTop() - 4f,
                getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - 4f) / 2;
        x = getMeasuredWidth() / 2;
        y = getMeasuredHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(x, y, radius, mBallPaint);
        //canvas.drawText(num,x-mTextBounds.width()/2,y-mTextBounds.height()+getPaddingTop(),mTextPaint);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        // 计算文字高度
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
        // 计算文字baseline
        float textBaseY = getMeasuredHeight() - (getMeasuredHeight() - fontHeight) / 2 - fontMetrics.bottom;
        if (!TextUtils.isEmpty(num)) {
            canvas.drawText(num, x - mTextBounds.width() / 2, textBaseY, mTextPaint);
        }
    }


    public void setBallNum(String num) {
        this.num = num;
        initTextBound();
        //invalidateTextPaintAndMeasurements();
        invalidate();
    }

    public String getBallNum() {
        return this.num;
    }

    public void init(String ballNum,int textSize,int ballColor,boolean checked) {
        this.num = ballNum;
        this.textSize = textSize;
        this.ballColor = ballColor;
        this.isSelected = checked;

        initTextBound();
        resetColor();
        invalidate();
    }


    public interface BallViewSelectedListener {
        void OnBallViewSelected(XXBallView view,boolean isSelected);
    }


    private void resetColor() {
        if (isSelected) {
            mTextPaint.setColor(Color.WHITE);
            mBallPaint.setStyle(Paint.Style.FILL);
        } else {
            mBallPaint.setStyle(Paint.Style.STROKE);
            mBallPaint.setStrokeWidth(4f);
            mTextPaint.setColor(ballColor);
        }
        mBallPaint.setColor(ballColor);
    }

    public void setBallColor(int ballColor) {
        this.ballColor = ballColor;
        resetColor();
        invalidate();
    }

    public void setBallTextSize(int textSize) {
        this.textSize = textSize;
        invalidate();
    }

    public void setBallChecked(boolean isSelected) {
        this.isSelected = isSelected;
        resetColor();
        invalidate();
    }

    public void setIsSelectable(boolean isSelectable) {
        this.isSelectable = isSelectable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isSelectable) {
                    isSelected = !isSelected;
                    resetColor();
                    invalidate();
                    if (ballViewSelectedListener != null) {
                        ballViewSelectedListener.OnBallViewSelected(this,isSelected);
                    }
                }
                //invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setBallViewSelectedListener(BallViewSelectedListener ballViewSelectedListener) {
        this.ballViewSelectedListener = ballViewSelectedListener;
    }
}
