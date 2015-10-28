package com.ldxx.xxalib.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ldxx.android.base.adapter.XXBaseAdapter;
import com.ldxx.android.base.bean.XXViewHolder;
import com.ldxx.xxalib.R;
import com.ldxx.xxalib.beans.XXItem;
import com.ldxx.xxalib.utils.LoadDataUtils;

import java.util.List;

/**
 *
 */
public class CaseDemoFragment extends Fragment {
    private ListView listView;

    public CaseDemoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CaseDemoFragment.
     */
    public static CaseDemoFragment newInstance() {
        CaseDemoFragment fragment = new CaseDemoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_case_demo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.list);
        init();
    }

    private void init() {
        final List<XXItem> data = LoadDataUtils.loadData(getActivity(), R.array.case_fragment_title,
                R.array.case_fragment_description, R.array.case_fragment_value);
        listView.setAdapter(new XXBaseAdapter<XXItem>(getActivity(), data, R.layout.fragment_custom_item) {
            @Override
            public void convert(XXViewHolder helper, XXItem item) {
                helper.setText(R.id.title, item.getTitle()).setText(R.id.contents, item.getDescription());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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


}
