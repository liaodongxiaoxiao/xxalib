package com.ldxx.android.base.bean;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
    private View convertView;
    private int position;

    public XXViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.position = position;
        this.views = new SparseArray<>();
        this.convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.convertView.setTag(this);
    }

    public static XXViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new XXViewHolder(context, parent, layoutId, position);
        }
        return (XXViewHolder) convertView.getTag();
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
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
     * 给指定的TextView设置文字
     *
     * @param viewId TextView的id
     * @param text   设置的文本
     * @return 返回holder本身
     */
    public XXViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 给指定的TextView设置文字
     *
     * @param viewId TextView的id
     * @param textId 设置文本的资源id
     * @return 返回holder本身
     */
    public XXViewHolder setText(int viewId, int textId) {
        TextView view = getView(viewId);
        view.setText(textId);
        return this;
    }

    /**
     * 给指定的ImageView设置图片
     *
     * @param viewId     ImageView的id
     * @param drawableId 设置图片的资源id
     * @return 返回holder本身
     */
    public XXViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 给指定的ImageView设置图片
     *
     * @param viewId   ImageView的id
     * @param drawable 设置图片的Drawable对象
     * @return 返回holder本身
     */
    public XXViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }
}
