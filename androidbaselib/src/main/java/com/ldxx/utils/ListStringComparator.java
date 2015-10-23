package com.ldxx.utils;

import java.util.Comparator;

/**
 * Created by LDXX on 2015/10/23.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class ListStringComparator implements Comparator<String> {
    private boolean desc = false;

    public ListStringComparator() {
    }

    public ListStringComparator(boolean desc) {
        this.desc = desc;
    }

    @Override
    public int compare(String lhs, String rhs) {
        try {
            if (desc) {
                return -(Integer.parseInt(lhs) - Integer.parseInt(rhs));
            }
            return Integer.parseInt(lhs) - Integer.parseInt(rhs);
        } catch (Exception e) {
            return -1;
        }

    }

}
