package com.ldxx.android.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by LDXX on 2016/3/4.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class XXRecyclerView extends XRecyclerView {
    private static final String TAG = "XXRecyclerView";
    private View emptyView;

    public XXRecyclerView(Context context) {
        this(context, null);
    }

    public XXRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XXRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    final AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            if (emptyView != null) {
                Log.e(TAG, "onChanged: " + (getAdapter().getItemCount() > 0));
                emptyView.setVisibility(getAdapter().getItemCount() > 0 ? GONE : VISIBLE);
            }
        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
        observer.onChanged();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }
}
