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
            }
        });


    }

    private void initViewByBalls() {
        String[] bs = ballNums.split(",");
        for (String num : bs) {
            balls.add(new BallInfo(num, ballColor));
        }
        adapter.notifyDataSetChanged();
    }


    private void initViewByRang() {
        String num;
        for (int i = startBallNum; i <= endBallNum; i++) {
            num = StringUtils.getFullBall(i + "");
            balls.add(new BallInfo(num, ballColor));
        }
        adapter.notifyDataSetChanged();
    }

    private boolean isChecked(String ballNum) {
        return checkAll || !TextUtils.isEmpty(ballNum) && !TextUtils.isEmpty(checkedNums) && checkedNums.contains(ballNum)
                || selectedBalls.contains(ballNum);
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

    /**
     * 设置球码
     * @param ball 球码，对个值以英文逗号隔开
     */
    public void setBalls(String ball) {
        if (TextUtils.isEmpty(ball)) {
            return;
        }
        this.ballNums = ball;
        //先清空，防止用在listview或RecleverView中出现串结果问题
        this.balls.clear();
        initViewByBalls();
    }

    public void setCheckedNums(String checkedNums) {
        this.checkedNums = checkedNums;
        adapter.notifyDataSetChanged();
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
            final ViewHolder viewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.xxballview_gridview_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.ballView = (XXBallView) convertView.findViewById(R.id.ball_view);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final BallInfo ball = balls.get(position);
            viewHolder.ballView.setIsSelectable(isSelectable);
            viewHolder.ballView.setText(ball.getNum());
            viewHolder.ballView.setBallColor(ball.getColor());
            viewHolder.ballView.setIsSelectable(false);
            if (ball.isChecked() || isChecked(ball.getNum())) {
                if(!selectedBalls.contains(ball.getNum())){
                    selectedBalls.add(ball.getNum());
                }
                viewHolder.ballView.setChecked(true);
            } else {
                viewHolder.ballView.setChecked(false);
                selectedBalls.remove(ball.getNum());
            }

            viewHolder.ballView.setTextSize(textSize);

            viewHolder.ballView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, ball.getNum(), Toast.LENGTH_SHORT).show();
                    if (isSelectable) {

                        if (viewHolder.ballView.isSelected()) {
                            viewHolder.ballView.setChecked(false);
                            selectedBalls.remove(ball.getNum());
                        } else {
                            viewHolder.ballView.setChecked(true);
                            selectedBalls.add(ball.getNum());
                        }
                    }
                }
            });
            return convertView;
        }

        class ViewHolder{
            private XXBallView ballView;
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

        public BallInfo(String num, int color) {
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
