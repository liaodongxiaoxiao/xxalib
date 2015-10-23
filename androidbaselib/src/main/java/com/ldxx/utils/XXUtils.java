package com.ldxx.utils;

import java.util.Collections;
import java.util.List;

/**
 * Created by LDXX on 2015/10/22.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class XXUtils {
    public static String listToString(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuilder s = new StringBuilder();
        for (String str : list) {
            s.append(str).append(",");
        }
        return s.substring(0, s.length() - 1);
    }

    public static String listToOrderString(List<String> list, boolean desc) {
        Collections.sort(list, new ListStringComparator(desc));
        return listToString(list);
    }

    public static String listToAscString(List<String> list) {
        return listToOrderString(list, false);
    }
}
