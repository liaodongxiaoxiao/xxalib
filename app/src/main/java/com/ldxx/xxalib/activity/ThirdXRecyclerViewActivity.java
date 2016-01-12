package com.ldxx.xxalib.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ldxx.xxalib.R;
import com.ldxx.xxalib.adapter.XRecycleViewAdapter;
import com.ldxx.xxalib.beans.DividerItemDecoration;
import com.ldxx.xxalib.beans.XXNewsInfo;
import com.ldxx.xxalib.contentprovider.NewsContentProvider;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.novoda.simplechromecustomtabs.SimpleChromeCustomTabs;
import com.novoda.simplechromecustomtabs.navigation.IntentCustomizer;
import com.novoda.simplechromecustomtabs.navigation.NavigationFallback;
import com.novoda.simplechromecustomtabs.navigation.SimpleChromeCustomTabsIntentBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ThirdXRecyclerViewActivity extends AppCompatActivity {
    private static final String TAG = "ThirdXRecyclerViewActivity";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.x_recycler_view)
    XRecyclerView xRecyclerView;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private XRecycleViewAdapter adapter;
    private List<XXNewsInfo> mData = new ArrayList<>();
    private int pageNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_xrecycler_view);
        ButterKnife.bind(this);
        // 初始化SimpleChromeCustomTabs
        SimpleChromeCustomTabs.initialize(this);
        //设置toolbar
        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "共" + mData.size() + "条新闻", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        adapter = new XRecycleViewAdapter(this, mData);
        adapter.setItemClickListener(new XRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                SimpleChromeCustomTabs.getInstance().withFallback(navigationFallback)
                        .withIntentCustomizer(mCustomizer)
                        .navigateTo(Uri.parse(mData.get(position).getUrl()), ThirdXRecyclerViewActivity.this);
            }
        });


        xRecyclerView.setAdapter(adapter);
        xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        //设置分割线
        xRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                xRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                new LoadMore().execute();
            }
        });
    }

    private final NavigationFallback navigationFallback = new NavigationFallback() {
        @Override
        public void onFallbackNavigateTo(Uri url) {
            Toast.makeText(getApplicationContext(), "application_not_found", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Intent.ACTION_VIEW)
                    .setData(url)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    };

    private final IntentCustomizer mCustomizer = new IntentCustomizer(){

        @Override
        public SimpleChromeCustomTabsIntentBuilder onCustomiseIntent(SimpleChromeCustomTabsIntentBuilder builder) {
            return builder.showingTitle().withToolbarColor(getResources().getColor(R.color.colorPrimary));
        }
    };


    private void initData() {
        DbUtils db = DbUtils.create(getApplicationContext(), NewsContentProvider.DATABASE_NAME);
        try {
            List<XXNewsInfo> l = db.findAll(Selector.from(XXNewsInfo.class).orderBy("create_time", true).limit(5));
            if (l != null && !l.isEmpty()) {
                mData.addAll(l);
                pageNum = 1;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    class LoadMore extends AsyncTask<Void, Void, List<XXNewsInfo>> {

        @Override
        protected List<XXNewsInfo> doInBackground(Void... params) {
            DbUtils db = DbUtils.create(getApplicationContext(), NewsContentProvider.DATABASE_NAME);
            try {
                Thread.sleep(5000);
                int PAGE_SIZE = 5;
                List<XXNewsInfo> l = db.findAll(Selector.from(XXNewsInfo.class).orderBy("create_time", true).limit
                        (PAGE_SIZE).offset(PAGE_SIZE * pageNum));
                if (l != null && !l.isEmpty()) {

                    pageNum++;
                    return l;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<XXNewsInfo> xxNewsInfos) {
            super.onPostExecute(xxNewsInfos);
            if (xxNewsInfos != null && !xxNewsInfos.isEmpty()) {
                mData.addAll(xxNewsInfos);
                adapter.notifyDataSetChanged();
            }
            xRecyclerView.loadMoreComplete();
            fab.show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            fab.hide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SimpleChromeCustomTabs.getInstance().connectTo(this);
    }

    @Override
    protected void onPause() {
        SimpleChromeCustomTabs.getInstance().disconnectFrom(this);
        super.onPause();
    }
}
