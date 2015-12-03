package com.ldxx.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by LDXX on 2015/4/17.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
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

    /**
     * <p>Discription:Date格式化,将date对象转换成“yyyy-MM-dd”类型的字符串</p>
     *
     * @param date
     * @return
     * @author ldxx    2014-12-6
     * @update [修改人] [修改时间] [变更描述]
     */
    public static String dateToString(Date date) {
        return dateToString(date, PATTERN3);
    }

    /**
     * <p>Discription:日期格式化</p>
     *
     * @param date    待格式化的日期对象
     * @param pattern 格式化的格式
     * @return
     * @author ldxx    2014-12-6
     * @update [修改人] [修改时间] [变更描述]
     */
    public static String dateToString(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * <p>Discription:将“yyyy-MM-dd”类型的时间字符串，转换成Date对象</p>
     *
     * @param dateStr
     * @return
     * @author ldxx    2014年12月6日
     * @update [修改人] [修改时间] [变更描述]
     */
    public static Date stringToDate(String dateStr) {
        return stringToDate(dateStr, PATTERN3);
    }

    /**
     * <p>Discription:将指定的格式的时间字符串转换成Date对象</p>
     *
     * @param dateStr
     * @param pattern
     * @return
     * @author ldxx    2014年12月6日
     * @update [修改人] [修改时间] [变更描述]
     */
    public static Date stringToDate(String dateStr, String pattern) {
        if (StringUtils.isEmptyIncludeNullStr(dateStr)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
           return sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
