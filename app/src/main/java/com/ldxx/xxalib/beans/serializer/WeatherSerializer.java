package com.ldxx.xxalib.beans.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ldxx.xxalib.beans.Weather;

import java.lang.reflect.Type;

/**
 * Created by LDXX on 2016/1/5.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class WeatherSerializer implements JsonSerializer<Weather> {
    @Override
    public JsonElement serialize(Weather src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("aqi", src.getAqi());
        jsonObject.addProperty("city", src.getCity());
        jsonObject.addProperty("cityid", src.getCityid());
        jsonObject.addProperty("cur", src.getCurTemp());
        //jsonObject.addProperty("date", src.getDate()+"");
        jsonObject.addProperty("fengli", src.getFengli());
        jsonObject.addProperty("fengxiang", src.getFengxiang());
        jsonObject.addProperty("hightemp", src.getHightemp());
        jsonObject.addProperty("lowtemp", src.getLowtemp());
        jsonObject.addProperty("type", src.getType());
        jsonObject.addProperty("updatetime", src.getUpdatetime());
        jsonObject.addProperty("week", src.getWeek());
        jsonObject.addProperty("key", src.getKey());

        return jsonObject;
    }
}
