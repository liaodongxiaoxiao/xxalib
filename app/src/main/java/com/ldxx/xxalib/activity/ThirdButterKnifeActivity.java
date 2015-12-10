package com.ldxx.xxalib.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ldxx.xxalib.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Butter Knife demo
 */
public class ThirdButterKnifeActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.butter_knife_title)
    TextView textView;

    @BindString(R.string.s_use_butterknife)
    String sTitle;

    @Bind(R.id.list)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_butter_knife);
        //初始化
        ButterKnife.bind(this);

        //设置toolbar
        setSupportActionBar(toolbar);

        textView.setText(sTitle);

        TBKAdapter adapter = new TBKAdapter(this, getData());
        listView.setAdapter(adapter);
    }

    private List<TBKBean> getData() {
        List<TBKBean> data = new ArrayList<>();
        data.add(new TBKBean("@Bind", "@Bind(R.id.title) TextView title"));
        data.add(new TBKBean("@BindString", "@BindString(R.string.title) String title"));
        data.add(new TBKBean("@BindDrawable", "@BindDrawable(R.drawable.graphic) Drawable graphic"));
        data.add(new TBKBean("@BindColor", "@BindColor(R.color.red) int red"));
        data.add(new TBKBean("@BindDimen", " @BindDimen(R.dimen.spacer) Float spacer"));
        return data;
    }

    @OnClick(R.id.fab)
    void onClickFab(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    class TBKAdapter extends BaseAdapter {

        private List<TBKBean> data;
        private LayoutInflater inflater;

        public TBKAdapter(Context context, List<TBKBean> data) {
            this.data = data;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.content_item_butter_knife, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.titleTextView.setText(data.get(position).getTitle());
            holder.contentsTextView.setText(data.get(position).getContents());
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.title)
            TextView titleTextView;
            @Bind(R.id.contents)
            TextView contentsTextView;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    class TBKBean {
        private String title;
        private String contents;

        public TBKBean(String title, String contents) {
            this.contents = contents;
            this.title = title;
        }

        public String getContents() {
            return contents;
        }

        public String getTitle() {
            return title;
        }
    }

}
