package com.ldxx.android.base.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by LDXX on 2015/8/19.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class JsonTextView extends EditText {

    private JsonFormatUtils utils;
    private CharSequence initialValue;

    public JsonTextView(Context context) {
        this(context, null);
    }

    public JsonTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JsonTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        utils = new JsonFormatUtils();
    }

    private void init() {
        this.setHorizontalScrollBarEnabled(true);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        this.initialValue = text;
        //is not empty
        if (!TextUtils.isEmpty(text)) {
            String json = text.toString().trim();
            json = utils.format(json);
            super.setText(json, BufferType.NORMAL);
        } else {
            super.setText(text, BufferType.NORMAL);
        }
    }

    /**
     * get charSequence before format
     *
     * @return CharSequence
     */
    public CharSequence getInitialText() {
        return this.initialValue;
    }
}
