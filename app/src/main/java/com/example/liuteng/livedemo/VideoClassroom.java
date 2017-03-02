package com.example.liuteng.livedemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liuteng.livedemo.util.XlfLog;


public class VideoClassroom extends AppCompatActivity {


    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MainFragmentPagerAdapter fragmentPagerAdapter;
    private String[] titles = {"直播大厅", "视频中心"};
    private ImageView ivSearch;
    private ImageView ivShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_classroom);
        mTabLayout = (TabLayout) findViewById(R.id.id_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        ivSearch= (ImageView) findViewById(R.id.iv_live_search);
        ivShare= (ImageView) findViewById(R.id.iv_live_share);
        mViewPager.setOffscreenPageLimit(1);
        fragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(fragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        initTab();
        initListener();
    }

    private void initListener() {
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mTabLayout,"搜索被点击了",Snackbar.LENGTH_LONG).show();
            }
        });
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mTabLayout,"分享被点击了",Snackbar.LENGTH_LONG).show();
            }
        });
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
