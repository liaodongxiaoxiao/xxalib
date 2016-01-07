package com.ldxx.xxalib.beans.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ldxx.xxalib.beans.WeatherIndex;

import java.lang.reflect.Type;

/**
 * Created by LDXX on 2016/1/5.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class WeatherIndexSerializer implements JsonSerializer<WeatherIndex> {
    @Override
    public JsonElement serialize(WeatherIndex src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("date", src.getDate()+"");
        jsonObject.addProperty("cityid", src.getCityid());
        jsonObject.addProperty("code", src.getCode());
        jsonObject.addProperty("details", src.getDetails());
        jsonObject.addProperty("index", src.getIndex());
        jsonObject.addProperty("key", src.getKey());
        jsonObject.addProperty("name", src.getName());
        jsonObject.addProperty("otherName", src.getOtherName());
        return jsonObject;
    }
}
