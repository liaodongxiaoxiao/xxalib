package com.ldxx.xxalib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ldxx.xxalib.R;
import com.ldxx.xxalib.beans.XXNewsInfo;

/**
 * Created by LDXX on 2015/10/29.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class TAdapter extends HeaderRecyclerViewAdapter<RecyclerView.ViewHolder, XXNewsInfo,
        Footer> {
    private LayoutInflater mInflater;

    public TAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(mInflater.inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        return new FooterViewHolder(mInflater.inflate(R.layout.load_more_footer, parent, false));
    }


    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseViewHolder viewHolder = (BaseViewHolder) holder;
        XXNewsInfo ni = getItem(position);
        viewHolder.title.setText(ni.getTitle());
        viewHolder.content.setText(ni.getUrl());
        viewHolder.date.setText(ni.getCreate_time());


        /*//设置事件监听
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
        }*/
    }

    @Override
    protected void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
        Footer footer = getFooter();
        FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
        footerViewHolder.textView.setText(footer.getLoadMoreMessage());
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public FooterViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_load_more);;
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


}
