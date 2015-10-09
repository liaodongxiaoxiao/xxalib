package com.ldxx.android.base.view.adapter;

import android.content.Context;

import com.ldxx.android.base.R;
import com.ldxx.android.base.adapter.XXBaseAdapter;
import com.ldxx.android.base.bean.XXViewHolder;
import com.ldxx.android.base.view.DropDownMenuData;

import java.util.List;


/**
 * Created by WangZhuo on 2015/6/9.
 */
public class DropDownMenuAdapter extends XXBaseAdapter<DropDownMenuData> {
    private List<DropDownMenuData> data;
    private Context context;

    public DropDownMenuAdapter(Context context, List<DropDownMenuData> data, int itemLayoutId) {
        super(context, data, itemLayoutId);
        this.context = context;
        this.data = data;
    }

    @Override
    public void convert(XXViewHolder helper, DropDownMenuData item) {
        helper.setText(R.id.dropdownmenu_select_item_title, item.getKey());
    }
}
