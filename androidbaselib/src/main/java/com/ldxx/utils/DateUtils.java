package com.ldxx.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by WANGZHUO on 2015/4/17.
 */
public class DateUtils {
    public static final String PATTERN1 = "yyyy-MM-dd HH:mm:ss SSS";
    public static final String PATTERN2 = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN3 = "yyyy-MM-dd";
    public static final String PATTERN4 = "yyyyMMddHHmmss";

    public static String getCurrentTimeStr() {
        Calendar c = Calendar.getInstance();
        return dateToStr(c.getTime(), PATTERN2);
    }

    public static String dateToStr(Date date, String pattern) {
        DateFormat format2 = new SimpleDateFormat(pattern, Locale.getDefault());
        return format2.format(date);
    }

    public static String getCurrentDate() {

        return getCurrentDate(PATTERN3);
    }

    public static String getCurrentDate(String pattern) {
        Calendar c = Calendar.getInstance();
        return dateToStr(c.getTime(), pattern);
    }

}
