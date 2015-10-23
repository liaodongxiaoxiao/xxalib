package com.ldxx.android.base.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by LDXX on 2015/8/19.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class JsonTextView extends TextView {
    private CharSequence initialValue;
private Button btn;
    private int i = 0;

    private StringBuffer sb = new StringBuffer();

    public JsonTextView(Context context) {
        super(context);
    }

    public JsonTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JsonTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        this.initialValue = text;
        //is not empty
        if (!TextUtils.isEmpty(text)) {
            String json = text.toString().trim();
            json = format(json);
            super.setText(json, BufferType.NORMAL);
        } else {
            super.setText(text, BufferType.NORMAL);
        }
        i = 0;
        sb = new StringBuffer();
    }

    private String format(String str) {
        if (isJsonStr(str)) {
            toFormatStr(str);
            for (int n = i; n > 0; n--) {
                sb.append(getTabStr(n - 1)).append("}").append("\n");
            }
            return sb.toString();
        } else {
            return str;
        }
    }

    private void toFormatStr(String json) {


        String tmp;
        i++;
        sb.append("{\n");
        tmp = json.substring(1, json.length() - 1);
        if (isJsonStr(tmp)) {
            toFormatStr(tmp);
        } else {
            // 包括json子串
            if (tmp.indexOf("{") > 0) {
                String pre = tmp.substring(0, tmp.indexOf("{"));
                String[] pres = pre.split(",");
                for (int n = 0; n < pres.length - 1; n++) {
                    if (!isJsonStr(pres[n])) {
                        // i++;
                        sb.append(getTabStr(i)).append(pres[n]).append(",\n");
                    } else {
                        toFormatStr(pres[n]);
                    }
                }
                sb.append(getTabStr(i)).append(pres[pres.length - 1]);
                toFormatStr(tmp.substring(tmp.indexOf("{"), tmp.length()));
            } else {
                String[] tmps = tmp.split(",");

                for (int n = 0; n < tmps.length; n++) {
                    if (!isJsonStr(tmps[n])) {
                        if (n == tmps.length - 1) {
                            sb.append(getTabStr(i)).append(tmps[n]).append("\n");
                        } else {
                            sb.append(getTabStr(i)).append(tmps[n]).append(",\n");
                        }
                    } else {
                        toFormatStr(tmps[n]);
                    }
                }
            }
        }

    }

    private static boolean isJsonStr(String json) {
        return json.startsWith("{") && json.endsWith("}")
                && (numberOfStr(json, "[{]") == numberOfStr(json, "[}]"));
    }

    private static int numberOfStr(String str, String con) {
        str = " " + str + " ";
        if (str.endsWith(con)) {
            return str.split(con).length;
        } else {
            return str.split(con).length - 1;
        }
    }

    private static String getTabStr(int i) {
        String str = " ";
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < i * 4; j++) {
            sb.append(str);
        }
        return sb.toString();
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
