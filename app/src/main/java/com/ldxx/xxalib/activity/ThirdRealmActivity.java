package com.ldxx.xxalib.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ldxx.android.base.net.XXOKHttpUtils;
import com.ldxx.android.base.view.JsonTextView;
import com.ldxx.utils.DateUtils;
import com.ldxx.xxalib.R;
import com.ldxx.xxalib.beans.Weather;
import com.ldxx.xxalib.beans.WeatherIndex;
import com.ldxx.xxalib.beans.serializer.DateSerializer;
import com.ldxx.xxalib.beans.serializer.WeatherIndexSerializer;
import com.ldxx.xxalib.beans.serializer.WeatherSerializer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class ThirdRealmActivity extends AppCompatActivity {
    @Bind(R.id.current_weather)
    JsonTextView mCurrentWeather;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.chart)
    LineChartView mChart;

    private Realm mRealm;

    private ProgressDialog dialog;
    private final WeatherHandler handler = new WeatherHandler(this);
    public static final int TODAY = 11;
    public static final int HISTORY = 12;
    public static final int FORECAST = 13;
    public static final int INDEX = 19;
    public static final String TODAY_KEY = "today";
    public static final String HISTORY_KEY = "history";
    public static final String FORECAST_KEY = "forecast";
    private static final String TAG = "ThirdRealmActivity";
    public static final String KEY = "d6e91c2b841ef37858964106aa83749c";
    public static final String URL = "http://apis.baidu.com/apistore/weatherservice/recentweathers?cityid=101070101";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_realm);
        ButterKnife.bind(this);
        initRealm();
        setSupportActionBar(mToolbar);
        setWeatherInfo();
        initChart();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initRealm() {

        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .name("realm")
                .migration(new XXMigration())
                .schemaVersion(0)
                .build();
        //Realm.deleteRealm(config);

        mRealm = Realm.getInstance(config);
        mRealm.addChangeListener(weatherChangeListener);
    }

    private void initChart() {
        RealmQuery<Weather> query1 = mRealm.where(Weather.class);
        query1.lessThanOrEqualTo("date", DateUtils.getCurrentDateByPattern(DateUtils.PATTERN3));
        List<PointValue> highValues = new ArrayList<>();
        List<PointValue> lowValues = new ArrayList<>();
        List<AxisValue> axisValues = new ArrayList<>();

        RealmResults<Weather> weather = query1.findAllSorted("date", Sort.DESCENDING);
        if (weather.size() > 0) {
            int size = weather.size() > 3 ? 3 : weather.size();
            Weather wL;
            for (int i = 0; i < size; i++) {
                wL = weather.get(i);
                highValues.add(new PointValue(i, Integer.parseInt(wL.getHightemp().replaceAll("℃", ""))));
                lowValues.add(new PointValue(i, Integer.parseInt(wL.getLowtemp().replaceAll("℃", ""))));
                axisValues.add(new AxisValue(i).setLabel(DateUtils.dateToStr(wL.getDate(), "MM-dd")));
            }
        }
        RealmQuery<Weather> query2 = mRealm.where(Weather.class);
        query2.greaterThan("date", DateUtils.getCurrentDateByPattern(DateUtils.PATTERN3));
        RealmResults<Weather> hw = query2.findAll();
        if (hw.size() > 0) {
            int size = hw.size() > 4 ? 4 : hw.size();
            //size = highValues.size()>0?highValues.size()-1+size:size;
            //int j = highValues.size()>0?highValues.size()-1:0;
            int j = highValues.size();
            Weather wL;
            for (int i = 0; i < size; i++) {
                wL = hw.get(i);
                highValues.add(new PointValue(i + j, Integer.parseInt(wL.getHightemp().replaceAll("℃", ""))));
                lowValues.add(new PointValue(i + j, Integer.parseInt(wL.getLowtemp().replaceAll("℃", ""))));
                axisValues.add(new AxisValue(i + j).setLabel(DateUtils.dateToStr(wL.getDate(), "MM-dd")));
            }
        }


        //In most cased you can call data model methods in builder-pattern-like manner.
        Line lineLow = new Line(lowValues).setColor(Color.BLUE).setCubic(true);
        Line lineHigh = new Line(highValues).setColor(Color.RED).setCubic(true);
        List<Line> lines = new ArrayList<>();
        lines.add(lineLow);
        lines.add(lineHigh);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axisX = new Axis(axisValues);
        Axis axisY = new Axis().setHasLines(true);
        //axisX.setFormatter(new DateFormatter());
        axisX.setName("时间");
        axisY.setName("温度（°C）");
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        mChart.setLineChartData(data);


    }

    RealmChangeListener weatherChangeListener = new RealmChangeListener() {
        @Override
        public void onChange() {
            setWeatherInfo();
        }
    };

    private void setWeatherInfo() {
        RealmQuery<Weather> query = mRealm.where(Weather.class);

        query.equalTo("date", DateUtils.getCurrentDateByPattern(DateUtils.PATTERN3));
        RealmResults<Weather> weather = query.findAll();
        if (!weather.isEmpty()) {
            Weather w = weather.get(0);
            mCurrentWeather.setText(w.toString());
        }

    }

    @OnClick(R.id.refresh)
    void getWeather() {
        new WeatherAnsyTask().execute();

    }

    class WeatherAnsyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(ThirdRealmActivity.this, null, "正在获取天气信息");
        }

        @Override
        protected Void doInBackground(Void... params) {
            XXOKHttpUtils.Builder builder = new XXOKHttpUtils.Builder();
            builder.url(URL).header("apikey", KEY);
            XXOKHttpUtils utils = builder.build();
            try {
                String str = utils.getString();
                Log.e(TAG, "str: " + str);
                if (!TextUtils.isEmpty(str)) {
                    JSONObject obj = new JSONObject(str);
                    Gson gson = getGson();

                    if (obj.getInt("errNum") == 0) {
                        JSONObject retData = obj.getJSONObject("retData");
                        //Log.e(TAG, "doInBackground: "+retData );
                        String city = retData.getString("city");
                        String cityId = retData.getString("cityid");
                        JSONObject jb = retData.getJSONObject(TODAY_KEY);
                        JSONArray ja = jb.getJSONArray("index");
                        jb.remove("index");
                        Log.e(TAG, "jb: " + jb.toString());
                        final Weather today = gson.fromJson(jb.toString(), Weather.class);
                        Log.e(TAG, "ja: " + ja.toString());
                        List<WeatherIndex> list = gson.fromJson(ja.toString(), new TypeToken<List<WeatherIndex>>() {
                        }.getType());

                        Date date = today.getDate();
                        for (WeatherIndex index : list) {
                            index.setCityid(cityId);
                            index.setDate(date);
                        }

                        today.setCity(city);
                        today.setUpdatetime(DateUtils.getCurrentTimeStr());
                        today.setCityid(cityId);

                        Message msg = handler.obtainMessage(TODAY, today);
                        handler.sendMessage(msg);

                        msg = handler.obtainMessage(INDEX, list);
                        handler.sendMessage(msg);

                        List<Weather> histories = gson.fromJson(retData.getJSONArray(HISTORY_KEY).toString(), new TypeToken<List<Weather>>() {
                        }.getType());
                        for (Weather w : histories) {
                            w.setCityid(cityId);
                            w.setCity(city);
                            w.setUpdatetime(DateUtils.getCurrentTimeStr());
                        }

                        msg = handler.obtainMessage(HISTORY, histories);
                        handler.sendMessage(msg);

                        String forecastStr = retData.getJSONArray(FORECAST_KEY).toString();
                        Log.e(TAG, "forecast: " + forecastStr);
                        List<Weather> forecasts = gson.fromJson(forecastStr, new TypeToken<List<Weather>>() {
                        }.getType());
                        for (Weather w : histories) {
                            w.setCityid(cityId);
                            w.setCity(city);
                            w.setUpdatetime(DateUtils.getCurrentTimeStr());
                        }

                        msg = handler.obtainMessage(FORECAST, forecasts);
                        handler.sendMessage(msg);

                    }
                } else {
                    Log.e(TAG, "doInBackground: " + "查询失败");
                }


                //Log.e(TAG, "doInBackground: " + str);
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: " + e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

        }

    }

    private final class WeatherHandler extends Handler {
        private WeakReference<ThirdRealmActivity> weakReference;

        public WeatherHandler(ThirdRealmActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ThirdRealmActivity activity = weakReference.get();
            switch (msg.what) {
                case TODAY:
                    Weather weather = (Weather) msg.obj;
                    mRealm.beginTransaction();
                    //copyToRealm insert
                    Weather t = mRealm.copyToRealmOrUpdate(weather);
                    mRealm.commitTransaction();
                    break;
                case INDEX:
                    List<WeatherIndex> indexes = (List<WeatherIndex>) msg.obj;
                    mRealm.beginTransaction();
                    //copyToRealm insert
                    List<WeatherIndex> wi = mRealm.copyToRealmOrUpdate(indexes);
                    mRealm.commitTransaction();
                case HISTORY:
                    List<Weather> hWeathers = (List<Weather>) msg.obj;
                    mRealm.beginTransaction();
                    //copyToRealm insert
                    List<Weather> hws = mRealm.copyToRealmOrUpdate(hWeathers);
                    mRealm.commitTransaction();
                    break;
                case FORECAST:
                    List<Weather> fWeathers = (List<Weather>) msg.obj;
                    mRealm.beginTransaction();
                    //copyToRealm insert
                    List<Weather> fws = mRealm.copyToRealmOrUpdate(fWeathers);
                    mRealm.commitTransaction();
                    break;
                default:
                    break;

            }
            activity.initChart();
        }
    }

    private Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        });

        gsonBuilder.setDateFormat("yyyy-MM-dd");
        gsonBuilder.registerTypeAdapter(Weather.class, new WeatherSerializer())
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(WeatherIndex.class, new WeatherIndexSerializer());
        return gsonBuilder.create();
    }

    private class XXMigration implements RealmMigration {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            //RealmSchema schema = realm.getSchema();
            /*if (oldVersion == 0) {
                RealmObjectSchema weatherSchema = schema.get("Weather");
                weatherSchema.transform(new RealmObjectSchema.Function() {
                    @Override
                    public void apply(DynamicRealmObject dynamicRealmObject) {

                    }
                });
            }*/
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.removeChangeListener(weatherChangeListener);
        mRealm.close();
    }
}
