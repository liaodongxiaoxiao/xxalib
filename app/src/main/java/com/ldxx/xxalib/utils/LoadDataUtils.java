package com.ldxx.xxalib.utils;

import android.content.Context;

import com.ldxx.xxalib.R;
import com.ldxx.xxalib.beans.XXItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LDXX on 2015/10/21.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class LoadDataUtils {
    public static List<XXItem> loadData(Context context, int titleId, int descriptionId, int valuesId) {
        String[] titles = context.getResources().getStringArray(titleId);
        String[] description = context.getResources().getStringArray(descriptionId);
        String[] values = context.getResources().getStringArray(valuesId);
        final List<XXItem> data = new ArrayList<>();
        for (int i = 0, j = titles.length; i < j; i++) {
            data.add(new XXItem(titles[i], description[i], values[i]));
        }
        return data;
    }
}
