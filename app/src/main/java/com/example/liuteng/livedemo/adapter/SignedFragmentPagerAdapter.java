package com.example.liuteng.livedemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.liuteng.livedemo.fragment.NoStartedLectureFragment;
import com.example.liuteng.livedemo.fragment.OverLectureFragment;

/**
 * Created by 刘腾 on 2017/3/12.
 */

public class SignedFragmentPagerAdapter extends FragmentPagerAdapter {
    private int count = 2;

    public SignedFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new NoStartedLectureFragment();
        }else{
            return new OverLectureFragment();
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
