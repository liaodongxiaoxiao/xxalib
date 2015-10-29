package com.ldxx.xxalib.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ldxx.xxalib.R;
import com.ldxx.xxalib.adapter.Footer;
import com.ldxx.xxalib.adapter.OnRcvScrollListener;
import com.ldxx.xxalib.adapter.TAdapter;
import com.ldxx.xxalib.beans.DividerItemDecoration;
import com.ldxx.xxalib.beans.XXNewsInfo;
import com.ldxx.xxalib.contentprovider.NewsContentProvider;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

public class CommonRecyclerViewActivity extends AppCompatActivity {
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    //
    private List<XXNewsInfo> mData = new ArrayList<>();

    private TAdapter mAdapter;

    private final int PAGE_SIZE = 5;
    private int pageNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_recycler_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        initData();

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);

        mRefreshLayout.setColorSchemeColors(R.color.btn_color1, R.color.xx_blue, R.color.btn_color2, R.color
                .btn_color3);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadDataAsyncTask().execute();
            }
        });
        // get recycler view object
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //set layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TAdapter(CommonRecyclerViewActivity.this);
        mRecyclerView.setAdapter(mAdapter);

        //设置分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        /*mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(CommonRecyclerViewActivity.this, mData.get(position).getTitle(), Toast.LENGTH_SHORT)
                        .show();
            }
        });*/

        mRecyclerView.addOnScrollListener(new OnRcvScrollListener() {
            @Override
            public void onBottom() {
                super.onBottom();
                new LoadMore().execute();
            }
        });

        mAdapter.setFooter(new Footer("加载更多"));
        mAdapter.setItems(mData);
    }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class LoadDataAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //mLoading = true;
                Thread.sleep(4000l);
                //mLoading = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mRefreshLayout.setRefreshing(false);
        }
    }

    class LoadMore extends AsyncTask<Void, Void, List<XXNewsInfo>> {

        @Override
        protected List<XXNewsInfo> doInBackground(Void... params) {
            DbUtils db = DbUtils.create(getApplicationContext(), NewsContentProvider.DATABASE_NAME);
            try {
                Thread.sleep(5000);
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
                mAdapter.notifyDataSetChanged();
            }else {
                mAdapter.hideFooter();
            }
        }
    }
}
