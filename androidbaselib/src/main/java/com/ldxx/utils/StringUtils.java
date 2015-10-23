package com.ldxx.utils;import java.text.DecimalFormat;import java.util.Random;import java.util.regex.Matcher;import java.util.regex.Pattern;public class StringUtils {    /**     *     * @param str     * @return     */    public static boolean isEmpty(String str) {        if (null == str) {            return true;        }        str = str.trim();        return !(str.length() > 0 && !"null".equals(str));    }    /**     *     * @param ball     * @return     */    public static String getFullBall(String ball) {        System.out.println("getFullBall-->ball:"+ball);        if (isBallNumber(ball)) {            return new DecimalFormat("00").format(Integer.parseInt(ball));        }        else {//若传入不是球码，则自动生成随机球码            Random random = new Random();            return getFullBall((random.nextInt(10) + 1) + "");        }    }    public static boolean isBallNumber(String num) {        if (StringUtils.isZNumber(num)) {            if (num.length() <= 2) {                return Integer.parseInt(num) < 34;            }        }        return false;    }    /**     *     * @param num     * @return     */    public static boolean isZNumber(String num) {        if (num == null) {            return false;        }        Pattern p = Pattern.compile("^\\d*[1-9]\\d*$");        Matcher m = p.matcher(num);        return m.matches();    }    /**     *     */    public static String getMethodNameByAttr(String setOrGet, String attr) {        attr = attr.toLowerCase();        StringBuffer sb = new StringBuffer(setOrGet);        sb.append(attr.substring(0, 1).toUpperCase()).append(attr.substring(1));        return sb.toString();    }    public static boolean isEmptyTrimmed(String str) {        return str == null || str.toString().trim().isEmpty();    }    /**     *     * @param str     * @param separator     * @return     */    public static String getString(String[] str,String separator){        if (str == null) {            return "";        }        StringBuffer sb = new StringBuffer();        for (String s : str) {            sb.append(s).append(separator);        }        return sb.toString();    }    /**     *     * @param str     * @return     */    public static String getString(String[] str){        return getString(str," ");    }}