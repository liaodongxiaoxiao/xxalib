package com.ldxx.xxalib.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldxx.android.base.net.XXOKHttpException;
import com.ldxx.android.base.net.XXOKHttpUtils;
import com.ldxx.utils.DateUtils;
import com.ldxx.xxalib.R;
import com.ldxx.xxalib.beans.XXWeather;
import com.ldxx.xxalib.utils.WeatherUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CaseHttpActivity extends AppCompatActivity {
    public static final String DATABASE_NAME = "xxweather.db";
    private final String TAG = this.getClass().getSimpleName();
    private final String URL = "http://apis.baidu.com/apistore/weatherservice/cityid";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mEmptyInfo;
    //private JsonTextView mJsonTextView;

    private TextView mCurrentTemp;

    private TextView mTempRange;
    private TextView mWeatherType;
    private ImageView mWeatherImg;
    private TextView mDate;
    private TextView mLastUpdate;
    private TextView mCity;
    //
    private DbUtils db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_http);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        db = DbUtils.create(this, DATABASE_NAME);
        initView();
        //设置数据
        setWeatherInfo(loadData());
    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.btn_color1, R.color.xx_blue, R.color.btn_color2, R.color
                .btn_color3);
        mSwipeRefreshLayout.setDistanceToTriggerSync(200);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new VolleyAsyncTask().execute();
            }
        });

        mEmptyInfo = (TextView) findViewById(R.id.empty_info);
        //mJsonTextView = (JsonTextView) findViewById(R.id.json_info);

        mCurrentTemp = (TextView) findViewById(R.id.current_temp);
        mTempRange = (TextView) findViewById(R.id.temp_range);
        mWeatherType = (TextView) findViewById(R.id.weather_type);
        mWeatherImg = (ImageView) findViewById(R.id.weather_img);
        mDate = (TextView) findViewById(R.id.date);
        mLastUpdate = (TextView) findViewById(R.id.update_time);
        mCity = (TextView) findViewById(R.id.weather_city);
    }

    private XXWeather loadData() {
        XXWeather weather = null;
        try {
            db.findAll(Selector.from(XXWeather.class));
            weather = db.findFirst(Selector.from(XXWeather.class).where("date", "=", DateUtils.getCurrentDate()));
        } catch (Exception e) {
            Log.e(TAG, "查询本地天气数据出错", e);
        }
        return weather;
    }

    private void setWeatherInfo(XXWeather weather) {
        if (weather != null) {
            mCity.setVisibility(View.VISIBLE);
            mCurrentTemp.setVisibility(View.VISIBLE);
            mTempRange.setVisibility(View.VISIBLE);
            mWeatherImg.setVisibility(View.VISIBLE);
            mWeatherType.setVisibility(View.VISIBLE);
            mDate.setVisibility(View.VISIBLE);
            mLastUpdate.setVisibility(View.VISIBLE);

            mCurrentTemp.setText(weather.getCurTemp());
            mTempRange.setText(weather.getLowtemp() + "/" + weather.getHightemp());
            mWeatherImg.setImageResource(WeatherUtils.getWeatherImgByType(weather.getType()));
            mWeatherType.setText("PM2.5值:" + weather.getAqi());
            mDate.setText(DateUtils.getCurrentDate("MM月dd日 E"));
            mLastUpdate.setText("更新于:" + weather.getSavedate());

            mEmptyInfo.setVisibility(View.GONE);

        } else {
            mEmptyInfo.setVisibility(View.VISIBLE);

            mCity.setVisibility(View.GONE);
            mCurrentTemp.setVisibility(View.GONE);
            mTempRange.setVisibility(View.GONE);
            mWeatherImg.setVisibility(View.GONE);
            mWeatherType.setVisibility(View.GONE);
            mDate.setVisibility(View.GONE);
            mLastUpdate.setVisibility(View.GONE);

        }
    }

    class VolleyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Map<String, String> bodys = new HashMap<>();
            //bodys.put("cityname", "沈阳");
            //bodys.put("cityid", "");

            Map<String, String> headers = new HashMap<>();
            headers.put("apiKey", "d6e91c2b841ef37858964106aa83749c");

            try {
                XXOKHttpUtils.Builder builder = new XXOKHttpUtils.Builder();
                builder.url("http://apis.baidu.com/apistore/weatherservice/cityid")
                        .body("cityname","沈阳");
                //OKHttpPostUtils<XXWeather>.get(XXWeather.class).objectWithPost();
                //ResponseBody body = OKHttpBaseUtils.postBase(URL, headers, bodys);
                //Gson gson = new Gson();
                String json = builder.build().getString();

                //JsonParser parser = new JsonParser();
                //JsonElement tradeElement = parser.parse(body.charStream());
                Log.e(TAG, "doInBackground: "+json );
                //Log.e(TAG, "doInBackground: "+tradeElement );
                //JsonArray trade = tradeElement.getAsJsonArray();
                //Log.e(TAG, "doInBackground: "+trade );
            } catch (Exception e) {
                Log.e(TAG, "Error: " + e.getMessage());
                //mEmptyInfo.setVisibility(View.VISIBLE);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setWeatherInfo(loadData());
        }
    }

    public static void main(String[] args) throws IOException, XXOKHttpException {
        XXOKHttpUtils.Builder builder = new XXOKHttpUtils.Builder();
        builder.url("http://apis.baidu.com/apistore/weatherservice/cityid").header("apiKey", "d6e91c2b841ef37858964106aa83749c")
                .body("cityname","沈阳");
        String json = builder.build().getString();
        System.out.println(json);
    }

}
