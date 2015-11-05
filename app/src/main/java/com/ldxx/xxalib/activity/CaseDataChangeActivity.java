package com.ldxx.xxalib.activity;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ldxx.android.base.adapter.XXBaseAdapter;
import com.ldxx.android.base.bean.XXViewHolder;
import com.ldxx.android.base.utils.CommonUtils;
import com.ldxx.android.base.utils.XXAppUtils;
import com.ldxx.utils.DateUtils;
import com.ldxx.xxalib.R;
import com.ldxx.xxalib.beans.XXDateData;
import com.ldxx.xxalib.contentprovider.DateDataContentProvider;

import java.util.ArrayList;
import java.util.List;

public class CaseDataChangeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private final String TAG = this.getClass().getSimpleName();
    private TextView textView;
    private XXDateData mData;
    private List<XXDateData> datas = new ArrayList<>();
    private ListView mListView;
    private DataAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_data_change);
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
        textView = (TextView) findViewById(R.id.observer_text);
        mListView = (ListView) findViewById(R.id.list);
        mAdapter = new DataAdapter(this, datas, R.layout.fragment_custom_item);
        mListView.setAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this);
        initData();

        ContentResolver resolver = getContentResolver();
        resolver.registerContentObserver(DateDataContentProvider.CONTENT_URI, true, new DataContentObserver(handler));

    }

    private void initData() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(DateDataContentProvider.CONTENT_URI, null, null, null,
                DateDataContentProvider.COLUMN_DATA + " desc");

        if (cursor != null && cursor.moveToFirst()) {
            List<XXDateData> m = new ArrayList<>();
            do {
                m.add(new XXDateData(cursor.getString(cursor.getColumnIndex(DateDataContentProvider.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(DateDataContentProvider.COLUMN_DATA))));
            } while (cursor.moveToNext());

            cursor.close();
            datas.clear();
            datas.addAll(m);
            mAdapter.notifyDataSetChanged();

            Bundle bundle = new Bundle();
            bundle.putInt("count", m.size());
            bundle.putParcelable("data", m.get(0));
            Message msg = new Message();
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle b = msg.getData();
            if (b != null && b.containsKey("data")) {
                mData = b.getParcelable("data");
                textView.setText("共" + b.getInt("count") + "条记录：\n 最新的为：" + mData.toString());

            } else {
                textView.setText("无任何记录");
            }
        }
    };

    public void addData(View view) {
        ContentResolver resolver = getContentResolver();
        //Uri uri = Uri.parse("content://com.ljq.provider.personprovider/person");
        //添加一条记录
        ContentValues values = new ContentValues();
        values.put(DateDataContentProvider.COLUMN_ID, CommonUtils.getUUID());
        values.put(DateDataContentProvider.COLUMN_DATA, DateUtils.getCurrentTimeStr());
        resolver.insert(DateDataContentProvider.CONTENT_URI, values);
    }

    public void delData(View view) {
        if (mData != null) {
            ContentResolver resolver = getContentResolver();
            resolver.delete(DateDataContentProvider.CONTENT_URI, DateDataContentProvider.COLUMN_ID + "=?", new
                    String[]{mData.getD_id()});
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, DateDataContentProvider.CONTENT_URI, new String[]{DateDataContentProvider
                .COLUMN_ID, DateDataContentProvider.COLUMN_DATA}, null, null, DateDataContentProvider.COLUMN_DATA + "" +
                " desc");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        datas.clear();
        if (data != null && data.moveToFirst()) {
            do {
                datas.add(new XXDateData(data.getString(data.getColumnIndex(DateDataContentProvider.COLUMN_ID)),
                        data.getString(data.getColumnIndex(DateDataContentProvider.COLUMN_DATA))));
            } while (data.moveToNext());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        datas.clear();
        mAdapter.notifyDataSetChanged();
    }

    class DataContentObserver extends ContentObserver {

        private Handler handler;

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public DataContentObserver(Handler handler) {
            super(handler);
            this.handler = handler;
        }

        @Override
        public void onChange(boolean selfChange) {
            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(DateDataContentProvider.CONTENT_URI, null, null, null,
                    DateDataContentProvider.COLUMN_DATA + " desc");
            if (cursor != null && cursor.moveToFirst()) {
                String id = cursor.getString(cursor.getColumnIndex(DateDataContentProvider.COLUMN_ID));
                String data = cursor.getString(cursor.getColumnIndex(DateDataContentProvider.COLUMN_DATA));
                Bundle bundle = new Bundle();
                bundle.putInt("count", cursor.getCount());
                bundle.putParcelable("data", new XXDateData(id, data));
                bundle.putString(DateDataContentProvider.COLUMN_DATA, data);
                bundle.putString(DateDataContentProvider.COLUMN_ID, id);
                Message msg = new Message();
                msg.setData(bundle);
                handler.sendMessage(msg);
            } else {
                handler.sendEmptyMessage(1);
            }
        }


    }

    class DataAdapter extends XXBaseAdapter<XXDateData> {

        public DataAdapter(Context context, List<XXDateData> data, int itemLayoutId) {
            super(context, data, itemLayoutId);
        }

        @Override
        public void convert(XXViewHolder helper, XXDateData item) {
            helper.setText(R.id.contents, item.getD_id()).setText(R.id.title, item.getD_data());
        }
    }
}
