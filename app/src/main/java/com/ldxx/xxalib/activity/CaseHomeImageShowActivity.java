package com.ldxx.xxalib.activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ldxx.xxalib.R;
import com.ldxx.xxalib.beans.XXNewsInfo;
import com.ldxx.xxalib.contentprovider.NewsContentProvider;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class CaseHomeImageShowActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private ViewPager mViewPager;
    private SimpleDraweeView[] mImages = new SimpleDraweeView[5];
    private List<XXNewsInfo> mDatas = new ArrayList<>();
    private NewsViewPagerAdapter mAdapter;
    private CirclePageIndicator indicator;
    private TextView mTitle;

    private final String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_case_home_show);
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

        mTitle = (TextView) findViewById(R.id.news_title);

        mViewPager = (ViewPager) findViewById(R.id.news_pager);
        mAdapter = new NewsViewPagerAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPagerChangeListener());
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
        getSupportLoaderManager().initLoader(0, null, this);
        initImageViews();
    }

    private void initImageViews() {
        SimpleDraweeView sv;
        ViewGroup.LayoutParams params;
        for (int i = 0; i < 5; i++) {
            sv = new SimpleDraweeView(this);
            sv.setScaleType(SimpleDraweeView.ScaleType.FIT_CENTER);
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                    .MATCH_PARENT);
            sv.setLayoutParams(params);
            mImages[i] = sv;
        }
    }

    private void initData() {
        if (!mDatas.isEmpty()) {
            mTitle.setText(mDatas.get(mViewPager.getCurrentItem()).getTitle());
        } else {
            mTitle.setText("");
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(this, NewsContentProvider.CONTENT_URI,
                NewsContentProvider.ALL_PROJECTION, null, null, NewsContentProvider.COLUMN_CREATE_TIME + " desc");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {
            XXNewsInfo news;
            mDatas.clear();
            int i = 0;
            do {
                news = new XXNewsInfo(
                        cursor.getString(cursor.getColumnIndex(NewsContentProvider.COLUMN_CREATE_TIME)),
                        cursor.getString(cursor.getColumnIndex(NewsContentProvider.COLUMN_IMAGE_SRC)),
                        cursor.getString(cursor.getColumnIndex(NewsContentProvider.COLUMN_PID)),
                        cursor.getString(cursor.getColumnIndex(NewsContentProvider.COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(NewsContentProvider.COLUMN_URL))
                );
                mDatas.add(news);
                i++;
            } while (cursor.moveToNext() && i < 5);
            mAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mDatas.clear();
        mAdapter.notifyDataSetChanged();
    }

    class NewsViewPagerAdapter extends PagerAdapter {

        public NewsViewPagerAdapter() {
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImages[position]);
        }

        /**
         *
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            SimpleDraweeView imageView = mImages[position];
            Uri uri = Uri.parse(mDatas.get(position).getImage_src());
            imageView.setImageURI(uri);
            container.addView(imageView, 0);
            return imageView;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            initData();
        }
    }
    private int lastState;

    class ViewPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            /*Log.e(TAG, "onPageScrolled: position-" + position + " positionOffset-" + positionOffset +
                    " positionOffsetPixels-" + positionOffsetPixels);*/
        }

        @Override
        public void onPageSelected(int position) {
            mTitle.setText(mDatas.get(position).getTitle());
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                case ViewPager.SCROLL_STATE_SETTLING:
                    lastState = state;
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    if (lastState == ViewPager.SCROLL_STATE_DRAGGING) {
                        if (mViewPager.getCurrentItem() == mDatas.size() - 1) {
                            mViewPager.setCurrentItem(0);
                        } else if (mViewPager.getCurrentItem() == 0) {
                            mViewPager.setCurrentItem(mDatas.size());
                        }
                    }
                    break;
            }
        }
    }

}
