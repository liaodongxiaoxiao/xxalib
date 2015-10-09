package com.ldxx.android.base.bean;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *
 */
public class XXViewHolder {
    private SparseArray<View> views;
    private View convertView ;
    private int position;

    public XXViewHolder(Context context,ViewGroup parent,int layoutId,int position){
        this.position = position;
        this.views = new SparseArray<>();
        this.convertView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        this.convertView.setTag(this);
    }

    public  static XXViewHolder get(Context context,View convertView,ViewGroup parent,int layoutId,int position){
        if (convertView==null){
            return new XXViewHolder(context,parent,layoutId,position);
        }
        return (XXViewHolder) convertView.getTag();
    }

    public <T extends View> T getView(int viewId){
        View view = views.get(viewId);
        if(view==null){
            view = convertView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T) view;

    }

    public View getConvertView() {
        return convertView;
    }

    public int getPosition() {
        return position;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public XXViewHolder setText(int viewId, String text)
    {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public XXViewHolder setText(int viewId, int textId)
    {
        TextView view = getView(viewId);
        view.setText(textId);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public XXViewHolder setImageResource(int viewId, int drawableId)
    {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }
}
