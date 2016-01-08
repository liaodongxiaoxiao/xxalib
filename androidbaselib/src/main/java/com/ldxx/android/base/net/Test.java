package com.ldxx.android.base.net;

import com.ldxx.android.base.bean.XXPM25;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LDXX on 2015/12/24.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class Test {
    public static final String URL = "http://apis.baidu.com/apistore/aqiservice/aqi?city=沈阳";
    public static final String TRAIN_QUERY = "http://a.apix.cn/apixlife/ticket/rest?from=BJP&to=TJP&date=2015-12-28";
    public static final String U = "http://192.168.0.71:8080/XXLotteryMVC//recommend/getHistoryRecommends.do";

    public static void main(String[] args) {
        //trains();
        XXOKHttpUtils.Builder builder = new XXOKHttpUtils.Builder();
        Map<String, String> map = new HashMap<>();
        map.put("num", "5");
        builder.url(U).bodys(map);
        XXOKHttpUtils utils = builder.build();
        try {
            System.out.print(utils.getList().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void trains() {
        XXOKHttpUtils.Builder builder = new XXOKHttpUtils.Builder();
        builder.url(TRAIN_QUERY).header("apix-key", "2a45733404c34ede7f98768483440216");
        XXOKHttpUtils utils = builder.build();
        try {
            System.out.print(utils.getString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void pm25() {
        XXOKHttpUtils.Builder builder = new XXOKHttpUtils.Builder();
        //
        builder.obj(XXPM25.class)
                .url(URL)
                .header("apikey", "d6e91c2b841ef37858964106aa83749c");
        //.body("city", "沈阳");
        XXOKHttpUtils utils = builder.build();
        try {
            XXPM25 pm = utils.getObject();
            System.out.print(pm.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
