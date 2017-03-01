package com.example.liuteng.livedemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.liuteng.livedemo.fragment.LiveRadioFragment;
import com.example.liuteng.livedemo.fragment.RecordRadioFragment;

/**
 * author�� on 2016/9/8 09:43
 * email��
 * desc��slidingtab
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private int count = 2;

    public MainFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new LiveRadioFragment();
        }else{
            return new RecordRadioFragment();
        }
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Tab"+position;
    }
}
