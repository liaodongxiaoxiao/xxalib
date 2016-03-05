package com.ldxx.xxalib.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ldxx.xxalib.R;
import com.ldxx.xxalib.beans.XXNewsInfo;

import java.util.List;

/**
 * Created by LDXX on 2015/12/23.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class XRecycleViewAdapter extends RecyclerView.Adapter<XRecycleViewAdapter.XViewHolder> {
    private static final String TAG = "XRecycleViewAdapter";
    private List<XXNewsInfo> data;
    private LayoutInflater inflater;
    private OnItemClickListener mItemClickListener;

    public XRecycleViewAdapter(Context context, List<XXNewsInfo> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
        Fresco.initialize(context);
    }

    @Override
    public XViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new XViewHolder(inflater.inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(XViewHolder holder, final int position) {
        Log.e(TAG, "onBindViewHolder: "+position );
        XXNewsInfo ni = data.get(position);
        holder.title.setText(ni.getTitle());
        holder.content.setText(ni.getUrl());
        holder.date.setText(ni.getCreate_time());
        Uri uri = Uri.parse(ni.getImage_src());
        holder.imageView.setImageURI(uri);
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.OnItemClick(v, position);
                }
            });
        }

    }

    public void addDatas(List<XXNewsInfo> newsInfos) {
        data.addAll(newsInfos);
        for (XXNewsInfo nw : newsInfos) {
            data.add(data.size() - 1, nw);
            notifyItemInserted(data.size() - 1);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class XViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView imageView;
        private TextView title;
        private TextView content;
        private TextView date;
        private TextView read;
        private TextView love;

        public XViewHolder(View itemView) {
            super(itemView);
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.r_img);
            title = (TextView) itemView.findViewById(R.id.r_title);
            content = (TextView) itemView.findViewById(R.id.r_content);
            date = (TextView) itemView.findViewById(R.id.r_date);
            read = (TextView) itemView.findViewById(R.id.r_read);
            love = (TextView) itemView.findViewById(R.id.r_love);

        }
    }

    public interface OnItemClickListener {
        void OnItemClick(View view, int position);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
