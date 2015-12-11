package com.ldxx.android.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.GridLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.widget.LinearLayout;

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
    private String TAG = this.getClass().getSimpleName();
    private List<String> selectedBalls = new ArrayList<>();
    //private Map<String, XXBallView> ballViews = new HashMap<>();
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

    private boolean chackAll = false;

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
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.XXBallViewLayout, defStyle, 0);
        try {
            ballColor = a.getColor(R.styleable.XXBallViewLayout_ball_color, Color.RED);

            textSize = (int) a.getDimension(R.styleable.XXBallViewLayout_text_size,
                    XXDensityUtils.sp2px(context, 14));
            ballDiameter = (int) a.getDimension(R.styleable.XXBallViewLayout_ballDiameter,
                    (int) Math.ceil(2.5 * textSize));

            isSelectable = a.getBoolean(R.styleable.XXBallViewLayout_selectable, false);
            startBallNum = a.getInt(R.styleable.XXBallViewLayout_startBallNum, startBallNum);
            endBallNum = a.getInt(R.styleable.XXBallViewLayout_endBallNum, endBallNum);
            ballNums = a.getString(R.styleable.XXBallViewLayout_ballNums);
            checkedNums = a.getString(R.styleable.XXBallViewLayout_checktedNums);
            columns = a.getInt(R.styleable.XXBallViewLayout_columns, 6);
            chackAll = a.getBoolean(R.styleable.XXBallViewLayout_checkAll, false);
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
        LayoutParams params = new LayoutParams();
        params.width = LayoutParams.MATCH_PARENT;
        params.height = LayoutParams.WRAP_CONTENT;

        params.setGravity(Gravity.CENTER);
        this.setLayoutParams(params);
    }

    private int padding;

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        padding = (width - columns * ballDiameter) / (columns - 1);
        //Log.e(TAG, "onMeasure: " + padding);
    }

    private void initViewByBalls() {
        String[] balls = ballNums.split(",");
        for (String num : balls) {
            initBallView(num);
        }
    }

    public void setBalls(String ball) {
        if (TextUtils.isEmpty(ball)) {
            return;
        }
        this.ballNums = ball;
        initViewByBalls();
    }

    private void initViewByRang() {
        for (int i = startBallNum; i <= endBallNum; i++) {
            initBallView(StringUtils.getFullBall(i + ""));
        }
    }

    public void addBallView(String ballNum, int ballColor, boolean isChecked) {
        initBallView(ballNum, ballColor, isChecked);
    }

    private void initBallView(String ballNum) {
        initBallView(ballNum, ballColor, isChecked(ballNum));
    }

    private void initBallView(String ballNum, int ballColor, boolean isChecked) {
        XXBallView ballView = new XXBallView(context);
        if(chackAll&&!isChecked){
            isChecked=true;
        }
        ballView.init(ballNum, textSize, ballColor, isChecked);//
        if(isChecked){
            selectedBalls.add(ballNum);
        }
        if (isSelectable) {
            ballView.setBallViewSelectedListener(new XXBallView.BallViewSelectedListener() {
                @Override
                public void OnBallViewSelected(XXBallView view, boolean isSelected) {
                    if (isSelected) {
                        selectedBalls.add(view.getBallNum());
                    } else {
                        selectedBalls.remove(view.getBallNum());
                    }
                }
            });
        }

        //ballViews.put(ballNum, ballView);
        ballView.setIsSelectable(isSelectable);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ballDiameter, ballDiameter);

        ballView.setPadding(padding, 0, padding, 10);
        ballView.setLayoutParams(params);
        ballKey.put(ballKey.size(), ballNum);

        this.addView(ballView);
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
}
