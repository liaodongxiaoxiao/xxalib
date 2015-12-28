package com.ldxx.xxalib.service;

import android.app.IntentService;
import android.content.Intent;

import com.ldxx.android.base.utils.CommonUtils;
import com.ldxx.android.base.utils.XXLog;
import com.ldxx.utils.DateUtils;
import com.ldxx.xxalib.beans.XXNewsInfo;
import com.ldxx.xxalib.contentprovider.NewsContentProvider;
import com.lidroid.xutils.DbUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;

/**
 *
 */
public class LoadDataIntentService extends IntentService {

    private String TAG = this.getClass().getSimpleName();

    public LoadDataIntentService() {
        super("LoadDataIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        XXLog.e(TAG, "Service .....");
        int i = 0;
        if (intent != null) {
            //final String action = intent.getAction();
            DbUtils db = DbUtils.create(getApplicationContext(), NewsContentProvider.DATABASE_NAME);

            try {
                Document doc = Jsoup.connect("http://sports.sina.com.cn/g/premierleague/")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0").timeout(30000).get();
                Elements es = doc.select("#J_Focus_Player_Wrap").select(".banner_img");

                Iterator<Element> iterator = es.iterator();
                Element e;
                Element aE;
                XXNewsInfo news = new XXNewsInfo();
                while (iterator.hasNext()) {
                    e = iterator.next();
                    aE = e.select("a").first();
                    news.setPid(CommonUtils.getUUID());
                    news.setImage_src(e.select("img").first().attr("src"));
                    news.setTitle(aE.attr("title"));
                    news.setUrl(aE.attr("href"));
                    news.setCreate_time(DateUtils.getCurrentTimeStr());
                    try {
                        db.save(news);
                    } catch (Exception eS) {
                        i++;
                    }

                }
            } catch (Exception e) {
                XXLog.d(TAG, e.getMessage(), e);
            } finally {
                XXLog.e(TAG, "新插入" + (5 - i) + "条数据");
                stopSelf();
            }
        }
    }


}
