package com.ldxx.android.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.GridLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;

import com.ldxx.android.base.R;
import com.ldxx.android.base.utils.XXDensityUtils;
import com.ldxx.utils.StringUtils;
import com.ldxx.utils.XXUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LDXX on 2015/10/22.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class XXBallViewLayout extends GridLayout {
    //private String TAG = this.getClass().getSimpleName();
    private List<String> selectedBalls = new ArrayList<>();
    private SparseArray<String> ballKey = new SparseArray<>();


    private boolean isSelectable = false;
    private int ballColor = Color.RED;
    private int textSize = 14;
    private int startBallNum = 0;
    private int endBallNum = 0;
    private String ballNums = "";
    private String checkedNums = "";
    private int ballDiameter;
    private int columns;

    private int columnSpace = 5;
    private int rowSpace = 5;

    private boolean checkAll = false;


    private Context context;

    public XXBallViewLayout(Context context) {
        super(context);
        this.context = context;
    }

    public XXBallViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs, 0);
    }

    public XXBallViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.setWillNotDraw(false);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.XXBallViewLayout, defStyle, 0);
        int scale = (int) context.getResources().getDisplayMetrics().density;
        try {
            ballColor = a.getColor(R.styleable.XXBallViewLayout_ballColor, Color.RED);

            textSize = (int) a.getDimension(R.styleable.XXBallViewLayout_textSize,
                    XXDensityUtils.sp2px(context, 14));

            isSelectable = a.getBoolean(R.styleable.XXBallViewLayout_selectable, false);
            startBallNum = a.getInt(R.styleable.XXBallViewLayout_startBallNum, startBallNum);
            endBallNum = a.getInt(R.styleable.XXBallViewLayout_endBallNum, endBallNum);
            ballNums = a.getString(R.styleable.XXBallViewLayout_ballNums);
            checkedNums = a.getString(R.styleable.XXBallViewLayout_checkedNums);
            columns = a.getInt(R.styleable.XXBallViewLayout_columns, 6);
            checkAll = a.getBoolean(R.styleable.XXBallViewLayout_checkAll, false);
            ballDiameter = (int) a.getDimension(R.styleable.XXBallViewLayout_ballDiameter, 0);
            float c = a.getDimension(R.styleable.XXBallViewLayout_columnSpace, 0);
            if (c > 0) {
                columnSpace = (int) (c / scale);
            }

            float r = a.getDimension(R.styleable.XXBallViewLayout_rowSpace, 0);
            if (r > 0) {
                rowSpace = (int) (r / scale);
            }
            this.setColumnCount(columns);
        } finally {
            a.recycle();
        }

        if (startBallNum != 0 && endBallNum != 0 && startBallNum <= endBallNum) {
            initViewByRang();
            //fillView();
        } else if (!TextUtils.isEmpty(ballNums)) {
            initViewByBalls();
            //fillView();
        }

        //this.setAlignmentMode(GridLayout.CENTER);
        //LayoutParams params = new LayoutParams();
        //params.width = LayoutParams.MATCH_PARENT;
        //params.height = LayoutParams.WRAP_CONTENT;

        //params.setGravity(Gravity.CENTER);
        //this.setLayoutParams(params);
        this.setUseDefaultMargins(false);
        this.setAlignmentMode(GridLayout.ALIGN_MARGINS);
        this.setRowOrderPreserved(false);
        //this.setBackgroundColor(Color.GRAY);
    }


    private void initViewByBalls() {
        String[] balls = ballNums.split(",");
        for (String num : balls) {
            //initBallView(num);
            addBallView(num);
        }
        requestLayout();
    }


    private void initViewByRang() {
        for (int i = startBallNum; i <= endBallNum; i++) {
            addBallView(StringUtils.getFullBall(i + ""));
        }
        requestLayout();
    }

    public void addBallView(String ballNum, int ballColor, boolean isChecked) {
        fillView(ballNum, ballColor, isChecked);
        requestLayout();
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
            measuredWidth = childView.getMeasuredWidth() * columns + columnSpace * (columns - 1);
            measuredHeight = childView.getMeasuredHeight() * getRowCount() + rowSpace * (getRowCount() - 1);
            setMeasuredDimension(measuredWidth, measuredHeight);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measuredHeight = childView.getMeasuredHeight() * getRowCount() + rowSpace * (getRowCount() - 1);
            setMeasuredDimension(widthSpecSize, measuredHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth() * columns + columnSpace * (columns - 1);
            setMeasuredDimension(measuredWidth, heightSpecSize);
        } else {
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        }

    }


    /*@Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            int childLeft = getPaddingLeft()/scale;
            int childTop = getPaddingTop()/scale;
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            int childWidth;
            int childHeight;

            childWidth = (int) ((width - getPaddingLeft()/scale - getPaddingRight()/scale - (columns - 1) * columnSpace) / columns);
            childHeight = (int) ((height - getPaddingTop()/scale - getPaddingBottom()/scale - (getRowCount() - 1) * rowSpace) / getRowCount());

            final int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = getChildAt(i);
                //不是gone就得占位
                if (child.getVisibility() != GONE) {
                    child.layout(childLeft, childTop, childWidth, childHeight);
                    if (i % columns == 0) {
                        childLeft = getPaddingLeft()/scale;
                        childTop += childHeight + rowSpace;
                    } else {
                        childLeft += childWidth + columnSpace;
                    }
                    //Log.e(TAG, "onLayout i:" + i + " childLeft:" + childLeft + " childTop:" + childTop);
                }

            }
        }*/
    XXBallView.BallViewSelectedListener listener = new XXBallView.BallViewSelectedListener() {
        @Override
        public void OnBallViewSelected(XXBallView view, boolean isSelected) {
            if (isSelected) {
                selectedBalls.add(view.getText());
            } else {
                selectedBalls.remove(view.getText());
            }
        }
    };
    private int balls;
    //private int row = 1;
    private int column = 1;

    private void fillView(String ballNums, int ballColor, boolean isChecked) {
        balls++;

        //BallView ballView;
        XXBallView ballV;
        boolean checked;
        //设置行数
        this.setRowCount((int) Math.ceil(balls * 1d / columns));
        //

        checked = checkAll && !isChecked || isChecked;

        if (checked) {
            selectedBalls.add(ballNums);
        }

        if (column == columns) {
            column = 1;
            //row++;
        } else {
            column++;
        }

        ballKey.put(ballKey.size(), ballNums);

        ballV = new XXBallView(context);
        ballV.setText(ballNums);
        ballV.setTextSize(textSize);
        ballV.setChecked(isChecked);
        ballV.setBallColor(ballColor);
        ballV.setPadding(columnSpace, rowSpace, columnSpace, rowSpace);
        if (ballDiameter > 0) {
            ballV.setRadius(ballDiameter / 2);
        }

        if (isSelectable) {
            ballV.setBallViewSelectedListener(listener);
            ballV.setIsSelectable(true);
        }
        Spec rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
        Spec colSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
        GridLayout.LayoutParams params = new LayoutParams(rowSpan, colSpan);
        this.addView(ballV, params);
    }


    public void addBallView(String ballNum) {
        fillView(ballNum, ballColor, isChecked(ballNum));
        requestLayout();
    }

    private boolean isChecked(String ballNum) {
        return !(TextUtils.isEmpty(ballNums) || TextUtils.isEmpty(checkedNums)) && checkedNums.contains(ballNum);
    }

    public String getSelectedBalls() {
        return XXUtils.listToString(selectedBalls);
    }

    public String getSelectedOrderBalls(boolean desc) {
        return XXUtils.listToOrderString(selectedBalls, desc);
    }

    public void setBalls(String ball) {
        if (TextUtils.isEmpty(ball)) {
            return;
        }
        this.ballNums = ball;
        initViewByBalls();
    }
}
