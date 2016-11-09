package com.ldxx.xxalib.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ldxx.xxalib.R;

public class CommonSearchViewActivity extends AppCompatActivity {
    private static final String TAG = "CommonSearchViewActivit";
    private static final String LINE = "LINE_KEY";

    private TextView content;
    private ProgressBar bar;
    private ScrollView scrollView;

    int lineHeight;
    int lines;

    private SharedPreferences preferences;

    int perPageLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_search_view);
        content = (TextView) findViewById(R.id.content);
        bar = (ProgressBar) findViewById(R.id.bar);

        scrollView = (ScrollView) findViewById(R.id.scroll);

        content.setMovementMethod(new ScrollingMovementMethod());

        preferences = getSharedPreferences("xx", Activity.MODE_PRIVATE);

        ViewTreeObserver vto = content.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = content.getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);
                lines = content.getLineCount();
                Log.e(TAG, "lines: " + lines);
                int height = content.getHeight();
                int scrollY = content.getScrollY();

                lineHeight = content.getLineHeight();
                Log.e(TAG, "height: " + height + " lineHeight:" + lineHeight);
                Layout layout = content.getLayout();

                int firstVisibleLineNumber = layout.getLineForVertical(scrollY);
                int lastVisibleLineNumber = layout.getLineForVertical(height + scrollY);
                Log.e(TAG, "onGlobalLayout: " + firstVisibleLineNumber + " " + lastVisibleLineNumber);
                perPageLine = scrollView.getHeight()/lineHeight;
                currentTopLine = 0;
            }
        });

        content.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int y = scrollView.getScrollY();
                int line = (int) Math.ceil((scrollView.getHeight() + y) / lineHeight);
                currentTopLine =line-perPageLine;
                //Log.e(TAG, scrollView.getHeight() + " onScrollChanged: " + y);
                Log.e(TAG, "onScrollChanged: " + line + " " + lines+" "+currentTopLine);
                Log.e(TAG, "---onScrollChanged: " + (line * 1d / lines) * 100);
                bar.setProgress((int) ((line * 1d / lines) * 100));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;
    }

    private int currentTopLine;
    //private int
    @Override
    protected void onStart() {
        super.onPause();
        currentTopLine = preferences.getInt(LINE,-1);
        if(currentTopLine>0){
            int y = content.getLayout().getLineTop(currentTopLine); // e.g. I want to scroll to line 40
            scrollView.scrollTo(0, y);
        }
    }


}
