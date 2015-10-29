package com.ldxx.xxalib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ldxx.xxalib.R;
import com.ldxx.xxalib.beans.XXNewsInfo;

import java.util.List;

/**
 * Created by WangZhuo on 2015/7/2.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private final String TAG = RecyclerViewAdapter.this.getClass().getSimpleName();
    //private Context context;
    private List<XXNewsInfo> data;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    public RecyclerViewAdapter(Context context, List<XXNewsInfo> data) {
        //this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder;
        if (getItemViewType(i) == TYPE_ITEM) {
            holder = new BaseViewHolder(inflater.inflate(R.layout.recycler_item, viewGroup, false));
        } else {
            holder = new FooterViewHolder(inflater.inflate(R.layout.load_more_footer, viewGroup, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            Log.e(TAG, "onBindViewHolder: item");
            onBindBaseViewHolder((BaseViewHolder) holder, position);
        } else {
            Log.e(TAG, "onBindViewHolder: foot");
            onBindFootViewHolder((FooterViewHolder) holder);
        }
    }

    private void onBindFootViewHolder(FooterViewHolder holder) {
        holder.textView.setText("加载更多...");
    }


    private void onBindBaseViewHolder(BaseViewHolder viewHolder, final int i) {

        XXNewsInfo ni = data.get(i);
        viewHolder.title.setText(ni.getTitle());
        viewHolder.content.setText(ni.getUrl());
        viewHolder.date.setText(ni.getCreate_time());
        //设置事件监听
        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, i);
                }
            });

        }
        if (onItemLongClickListener != null) {
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClickListener.onItemLongClick(v, i);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addDatas(List<XXNewsInfo> newsInfos) {
        data.addAll(newsInfos);
        for (XXNewsInfo nw : newsInfos) {
            data.add(data.size() - 1, nw);
            notifyItemInserted(data.size() - 1);
        }

    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public FooterViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title;
        private TextView content;
        private TextView date;
        private TextView read;
        private TextView love;

        public BaseViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.r_img);
            title = (TextView) itemView.findViewById(R.id.r_title);
            content = (TextView) itemView.findViewById(R.id.r_content);
            date = (TextView) itemView.findViewById(R.id.r_date);
            read = (TextView) itemView.findViewById(R.id.r_read);
            love = (TextView) itemView.findViewById(R.id.r_love);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == data.size() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }
}
