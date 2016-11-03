package com.ldxx.xxalib.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ldxx.xxalib.R;
import com.ldxx.xxalib.fragment.CommonPageContentsFragment;

public class CommonTabLayoutActivity extends AppCompatActivity {

    //private static final int RC_LOGIN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_tab_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initTab();

    }

    private void initTab() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        TabLayout tabLayout1 = (TabLayout) findViewById(R.id.tab1);
        ViewPager viewPager1 = (ViewPager) findViewById(R.id.viewpager1);
        viewPager1.setAdapter(new TabPagerAdapter(getSupportFragmentManager()));
        tabLayout1.setupWithViewPager(viewPager1);
    }

    class TabPagerAdapter extends FragmentPagerAdapter {
        final String[] TITLES = new String[]{"切尔西", "托特纳姆热刺", "曼城", "广州恒大淘宝", "辽宁宏运", "巴塞罗那"};

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return CommonPageContentsFragment.newInstance(TITLES[position]);
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
