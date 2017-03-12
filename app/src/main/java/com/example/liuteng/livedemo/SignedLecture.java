package com.example.liuteng.livedemo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liuteng.livedemo.adapter.SignedFragmentPagerAdapter;
import com.example.liuteng.livedemo.base.BaseActivity;
import com.example.liuteng.livedemo.util.XlfLog;

/**
 * Created by 刘腾 on 2017/3/12.
 */

public class SignedLecture extends BaseActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private SignedFragmentPagerAdapter fragmentPagerAdapter;
    private String[] titles = {"未开始", "已结束"};
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_signed_lecture;
    }

    @Override
    public void initView(View view) {
        mTabLayout = (TabLayout) findViewById(R.id.signed_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.signed_viewpager);
        mViewPager.setOffscreenPageLimit(1);
        fragmentPagerAdapter = new SignedFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(fragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        initTab();
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {
    }
    private void initTab() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                XlfLog.d("被选中的tab"+tab.getPosition());
                View currentView = tab.getCustomView();
                TextView textView = (TextView) currentView.findViewById(R.id.tv_tab);
                textView.setTextColor(Color.RED);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                XlfLog.d("未被选中的tab"+tab.getPosition());
                View currentView = tab.getCustomView();
                TextView textView = (TextView) currentView.findViewById(R.id.tv_tab);
                textView.setTextColor(Color.BLACK);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(R.layout.item_tab);
            View currentView = tab.getCustomView();
            TextView textView = (TextView) currentView.findViewById(R.id.tv_tab);
            textView.setText(titles[i]);
            if (i==0)
                textView.setTextColor(Color.RED);
        }
    }
}
