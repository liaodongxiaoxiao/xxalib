package com.ldxx.xxalib.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ldxx.xxalib.R;

/**
 *
 */
public class CommonPageContentsFragment extends Fragment {
    private static final String PARAM = "param1";

    private String mParam;


    public CommonPageContentsFragment() {
        // Required empty public constructor
    }

    /**
     *
     * @param param
     * @return
     */
    public static CommonPageContentsFragment newInstance(String param) {
        CommonPageContentsFragment fragment = new CommonPageContentsFragment();
        Bundle args = new Bundle();
        args.putString(PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_common_page_contents, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = (TextView) view.findViewById(R.id.pageContents);
        tv.setText(mParam);

    }
}
