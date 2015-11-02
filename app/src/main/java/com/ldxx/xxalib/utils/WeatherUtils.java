package com.ldxx.xxalib.utils;

import com.ldxx.utils.StringUtils;
import com.ldxx.xxalib.R;

/**
 * Created by LDXX on 2015/11/2.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class WeatherUtils {
    public static int getWeatherImgByType(String type) {
        if (StringUtils.isEmpty(type)) {
            return R.drawable.weather_sunny;
        }
        if ("晴".equals(type)) {
            return R.drawable.weather_sunny;
        } else if ("多云".equals(type)) {
            return R.drawable.weather_cloudy;
        } else if ("多云转晴".equals(type)) {
            return R.drawable.weather_cloudy_to_sunny;
        } else if (type.contains("雨")) {
            return R.drawable.weather_shower;
        } else if (type.contains("雪")) {
            return R.drawable.weather_snow;
        } else if ("霾".equals(type)) {
            return R.drawable.weather_haze;
        } else {
            return R.drawable.weather_sunny;
        }

    }
}
