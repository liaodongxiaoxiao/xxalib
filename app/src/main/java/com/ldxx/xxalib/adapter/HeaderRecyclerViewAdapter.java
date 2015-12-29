package com.ldxx.xxalib.adapter;

/**
 * Created by LDXX on 2015/10/29.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * RecyclerView.Adapter extension created to add header capability support and a generic List of
 * items really useful most of the cases. You should extend from this class and override
 * onCreateViewHolder to create your BallInfo instances and onBindViewHolder methods to draw your
 * user interface as you wish.
 * <p/>
 * The usage of List<T> items member is not mandatory. If you are going to provide your custom
 * implementation remember to override getItemCount method.
 */
public abstract class HeaderRecyclerViewAdapter<VH extends RecyclerView.ViewHolder, T, F>
        extends RecyclerView.Adapter<VH> {

    protected static final int TYPE_ITEM = -1;
    protected static final int TYPE_FOOTER = -3;

    private List<T> items = Collections.EMPTY_LIST;
    private F footer;
    private boolean showFooter = true;

    /**
     * Invokes onCreateHeaderViewHolder, onCreateItemViewHolder or onCreateFooterViewHolder methods
     * based on the view type param.
     */
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH viewHolder;
        if (isFooterType(viewType)) {
            viewHolder = onCreateFooterViewHolder(parent, viewType);
        } else {
            viewHolder = onCreateItemViewHolder(parent, viewType);
        }
        return viewHolder;
    }

    /**
     * Invokes onBindHeaderViewHolder, onBindItemViewHolder or onBindFooterViewHOlder methods based
     * on the position param.
     */
    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (isFooterPosition(position)) {
            onBindFooterViewHolder(holder, position);
        } else {
            onBindItemViewHolder(holder, position);
        }
    }

    /**
     * Returns the type associated to an item given a position passed as arguments. If the position
     * is related to a header item returns the constant TYPE_HEADER or TYPE_FOOTER if the position is
     * related to the footer, if not, returns TYPE_ITEM.
     * <p/>
     * If your application has to support different types override this method and provide your
     * implementation. Remember that TYPE_HEADER, TYPE_ITEM and TYPE_FOOTER are internal constants
     * can be used to identify an item given a position, try to use different values in your
     * application.
     */
    @Override
    public int getItemViewType(int position) {
        int viewType = TYPE_ITEM;
        if (isFooterPosition(position)) {
            viewType = TYPE_FOOTER;
        }
        return viewType;
    }

    /**
     * Returns the items list size if there is no a header configured or the size taking into account
     * that if a header or a footer is configured the number of items returned is going to include
     * this elements.
     */
    @Override
    public int getItemCount() {
        int size = items.size();

        if (hasFooter()) {
            size++;
        }
        return size;
    }


    public T getItem(int position) {

        return items.get(position);
    }

    public F getFooter() {
        return footer;
    }


    public void setItems(List<T> items) {
        validateItems(items);
        this.items = items;
    }

    public void setFooter(F footer) {
        this.footer = footer;
    }

    public void showFooter() {
        this.showFooter = true;
        notifyDataSetChanged();
    }

    public void hideFooter() {
        this.showFooter = false;
        notifyDataSetChanged();
    }


    protected abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    protected abstract VH onCreateFooterViewHolder(ViewGroup parent, int viewType);


    protected abstract void onBindItemViewHolder(VH holder, int position);

    protected abstract void onBindFooterViewHolder(VH holder, int position);


    public boolean isFooterPosition(int position) {
        int lastPosition = getItemCount() - 1;
        return hasFooter() && position == lastPosition;
    }

    protected boolean isFooterType(int viewType) {
        return viewType == TYPE_FOOTER;
    }


    /**
     * Returns true if the footer configured is not null.
     */
    protected boolean hasFooter() {
        return getFooter() != null && showFooter;
    }

    private void validateItems(List<T> items) {
        if (items == null) {
            throw new IllegalArgumentException("You can't use a null List<Item> instance.");
        }
    }
}
