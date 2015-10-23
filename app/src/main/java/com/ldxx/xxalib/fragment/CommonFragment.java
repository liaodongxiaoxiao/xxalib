package com.ldxx.xxalib.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.ldxx.android.base.adapter.XXBaseAdapter;
import com.ldxx.android.base.bean.XXViewHolder;
import com.ldxx.xxalib.R;
import com.ldxx.xxalib.beans.XXItem;
import com.ldxx.xxalib.utils.LoadDataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CommonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommonFragment extends Fragment {

    private GridView gridView;

    public CommonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CommonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommonFragment newInstance() {
        CommonFragment fragment = new CommonFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void init() {
        final List<XXItem> data = LoadDataUtils.loadData(getActivity(),R.array.common_fragment_title,
                R.array.common_fragment_description,R.array.common_fragment_value);

        gridView.setAdapter(new ItemAdapter(getActivity(), data, R.layout.fragment_common_item));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                XXItem item = data.get(position);
                try {
                    Class c = Class.forName(item.getValue());
                    getActivity().startActivity(new Intent(getActivity(), c));
                } catch (ClassNotFoundException e) {
                    Toast.makeText(getActivity(), "没有找到指定的Activity", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView);
        return view;
    }

    class ItemAdapter extends XXBaseAdapter<XXItem> {
        public ItemAdapter(Context context, List<XXItem> data, int itemLayoutId) {
            super(context, data, itemLayoutId);
        }

        @Override
        public void convert(XXViewHolder helper, XXItem item) {
            helper.setText(R.id.common_item_title, item.getTitle())
                    .setText(R.id.common_item_description, item.getDescription());
        }


    }


}
