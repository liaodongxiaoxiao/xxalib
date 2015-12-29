package com.ldxx.android.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.ldxx.android.base.R;
import com.ldxx.utils.StringUtils;
import com.ldxx.utils.XXUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 新的BallView layout
 * Created by LDXX on 2015/12/29.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class XXBallViewsLayout extends GridView {

    private static final String TAG = "XXBallViewsLayout";

    private boolean isSelectable = false;
    private int ballColor = Color.RED;
    private int textSize = 14;
    private int startBallNum = 0;
    private int endBallNum = 0;
    private String ballNums = "";
    private String checkedNums = "";
    private boolean checkAll = false;
    private List<String> selectedBalls = new ArrayList<>();
    private Context context;

    private List<BallInfo> balls = new ArrayList<>();
    private BallViewAdapter adapter;

    public XXBallViewsLayout(Context context) {
        this(context, null, 0);
    }

    public XXBallViewsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XXBallViewsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        this.context = context;
        // Load attributes
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.XXBallViewsLayout, defStyle, 0);
        int scale = (int) context.getResources().getDisplayMetrics().density;
        try {
            ballColor = a.getColor(R.styleable.XXBallViewsLayout_ballColor, Color.RED);

            textSize = (int) a.getDimension(R.styleable.XXBallViewsLayout_textSize,
                    scale * 14);

            isSelectable = a.getBoolean(R.styleable.XXBallViewsLayout_selectable, false);
            startBallNum = a.getInt(R.styleable.XXBallViewsLayout_startBallNum, startBallNum);
            endBallNum = a.getInt(R.styleable.XXBallViewsLayout_endBallNum, endBallNum);
            ballNums = a.getString(R.styleable.XXBallViewsLayout_ballNums);
            checkedNums = a.getString(R.styleable.XXBallViewsLayout_checkedNums);
            checkAll = a.getBoolean(R.styleable.XXBallViewsLayout_checkAll, false);

        } finally {
            a.recycle();
        }

        adapter = new BallViewAdapter();
        this.setAdapter(adapter);

        if (startBallNum != 0 && endBallNum != 0 && startBallNum <= endBallNum) {
            initViewByRang();
        } else if (!TextUtils.isEmpty(ballNums)) {
            initViewByBalls();
        }

        this.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemClick: " + position + " " + view.getClass().getName());

            }
        });


    }

    private void initViewByBalls() {
        String[] bs = ballNums.split(",");
        for (String num : bs) {
            balls.add(new BallInfo(num, ballColor, isChecked(num)));
        }
        adapter.notifyDataSetChanged();
    }


    private void initViewByRang() {
        String num;
        for (int i = startBallNum; i <= endBallNum; i++) {
            num = StringUtils.getFullBall(i + "");
            balls.add(new BallInfo(num, ballColor, isChecked(num)));
        }
        adapter.notifyDataSetChanged();
    }

    private boolean isChecked(String ballNum) {
        return checkAll || !(TextUtils.isEmpty(ballNums) || TextUtils.isEmpty(checkedNums)) && checkedNums.contains(ballNum);
    }

    public void addBallView(String s, int color, boolean isChecked) {
        balls.add(new BallInfo(s, color, isChecked));
        adapter.notifyDataSetChanged();
    }

    public String getSelectedBalls() {
        return XXUtils.listToString(selectedBalls);
    }

    public String getSelectedOrderBalls(boolean desc) {
        return XXUtils.listToOrderString(selectedBalls, desc);
    }

    /*******************************************************************/

    class BallViewAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public BallViewAdapter() {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return balls.size();
        }

        @Override
        public Object getItem(int position) {
            return balls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.xxballview_gridview_item, parent, false);
            }
            final XXBallView ballView = (XXBallView) convertView.findViewById(R.id.ball_view);
            final BallInfo ball = balls.get(position);
            ballView.setIsSelectable(isSelectable);
            ballView.setText(ball.getNum());
            ballView.setBallColor(ball.getColor());
            ballView.setIsSelectable(false);
            if (ball.isChecked()) {
                selectedBalls.add(ball.getNum());
                ballView.setChecked(true);
            } else {
                ballView.setChecked(false);
            }
            ballView.setChecked(ball.isChecked());
            ballView.setTextSize(textSize);

            ballView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, ball.getNum(), Toast.LENGTH_SHORT).show();
                    if (isSelectable) {

                        if (ballView.isSelected()) {
                            ballView.setChecked(false);
                            selectedBalls.remove(ball.getNum());
                        } else {
                            ballView.setChecked(true);
                            selectedBalls.add(ball.getNum());
                        }
                    }
                }
            });
            return convertView;
        }
    }

    class BallInfo {
        private String num;
        private int color;
        private boolean checked;

        public BallInfo(String num, int color, boolean checked) {
            this.checked = checked;
            this.color = color;
            this.num = num;
        }

        public boolean isChecked() {
            return checked;
        }

        public int getColor() {
            return color;
        }

        public String getNum() {
            return num;
        }

    }
}
