package com.ldxx.android.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.ldxx.android.base.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LDXX on 2015/12/28.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class DViewGridView extends GridView {
    private String text;
    private int textSize;
    private List<String> texts = new ArrayList<>();
    private float scale;
    DViewAdapter adapter;

    public DViewGridView(Context context) {
        this(context, null);
    }

    public DViewGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DViewGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        scale = context.getResources().getDisplayMetrics().density;
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.DViewGridView, defStyle, 0);
        try {
            text = a.getString(R.styleable.DViewGridView_text);
            textSize = (int) a.getDimension(R.styleable.DViewGridView_textSize, 14 * scale);
        } finally {
            a.recycle();
        }
        if (!TextUtils.isEmpty(text)) {
            String[] strs = text.split(",");
            for (String str : strs) {
                texts.add(str);
            }
        }

        adapter = new DViewAdapter(context);
        this.setAdapter(adapter);


    }

    public void addText(String str) {
        texts.add(str);
        adapter.notifyDataSetChanged();
    }

    class DViewAdapter extends BaseAdapter {
        Context context;
        LayoutInflater inflater;

        public DViewAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return texts.size();
        }

        @Override
        public Object getItem(int position) {
            return texts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.dgv_item, parent, false);
            }
            DView dv = (DView) convertView.findViewById(R.id.d_view);
            dv.setText(texts.get(position));
            dv.setTextSize(textSize);
            return convertView;
        }


    }
}
