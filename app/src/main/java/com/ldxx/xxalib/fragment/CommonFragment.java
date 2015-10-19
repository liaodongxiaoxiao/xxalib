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
        String[] titles = getResources().getStringArray(R.array.common_fragment_title);
        String[] description = getResources().getStringArray(R.array.common_fragment_description);
        String[] values = getResources().getStringArray(R.array.common_fragment_value);
        final List<Item> data = new ArrayList<>();
        for (int i = 0, j = titles.length; i < j; i++) {
            data.add(new Item(titles[i], description[i], values[i]));
        }

        gridView.setAdapter(new ItemAdapter(getActivity(), data, R.layout.fragment_common_item));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = data.get(position);
                try {
                    Class c = Class.forName(item.getValue());
                    getActivity().startActivity(new Intent(getActivity(),c));
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

    class ItemAdapter extends XXBaseAdapter<Item> {
        public ItemAdapter(Context context, List<Item> data, int itemLayoutId) {
            super(context, data, itemLayoutId);
        }

        @Override
        public void convert(XXViewHolder helper, Item item) {
            helper.setText(R.id.common_item_title, item.getTitle())
                    .setText(R.id.common_item_description, item.getDescription());
        }


    }

    class Item {
        private String title;
        private String value;
        private String description;

        public Item(String title, String description, String value) {
            this.title = title;
            this.value = value;
            this.description = description;
        }

        public Item() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }


}
