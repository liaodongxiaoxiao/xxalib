package com.ldxx.android.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.ldxx.android.base.bean.XXViewHolder;

import java.util.List;

/**
 * Created by WangZhuo on 2015/5/4.
 */
public abstract class XXBaseAdapter<T> extends BaseAdapter {
    private Context context;
    //private LayoutInflater inflater;
    private List<T> data;
    private int itemLayoutId;

    public XXBaseAdapter(Context context,List<T> data,int itemLayoutId) {
        this.context = context;
        this.data =data;
        this.itemLayoutId = itemLayoutId;
        //this.inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        XXViewHolder viewHolder = getViewHolder(convertView,parent,position);
        convert(viewHolder,getItem(position));

        return viewHolder.getConvertView();
    }

    public abstract void convert(XXViewHolder helper, T item);
    public XXViewHolder getViewHolder(View convertView,ViewGroup parent,int position){
        return XXViewHolder.get(this.context,convertView,parent,this.itemLayoutId,position);
    }
}
