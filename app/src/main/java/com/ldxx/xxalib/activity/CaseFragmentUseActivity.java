package com.ldxx.xxalib.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ldxx.xxalib.R;
import com.ldxx.xxalib.fragment.AFragment;
import com.ldxx.xxalib.fragment.BFragment;

public class CaseFragmentUseActivity extends AppCompatActivity {
    FragmentManager mManager;
    AFragment mAFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_fragment_use);
        mManager = getSupportFragmentManager();
        FragmentTransaction ft = mManager.beginTransaction();
        mAFragment = new AFragment();
        ft.replace(R.id.contents,mAFragment);
        ft.commit();
    }

    public void replaceFragment(View view) {
        FragmentTransaction ft = mManager.beginTransaction();
        ft.replace(R.id.contents,new BFragment());
        ft.commit();
    }

    public void hideShowFragment(View view) {
        FragmentTransaction ft = mManager.beginTransaction();
        ft.add(R.id.contents, new BFragment());
        ft.hide(mAFragment);
       // ft.show(thisfragment);
        ft.commit();
    }
}
