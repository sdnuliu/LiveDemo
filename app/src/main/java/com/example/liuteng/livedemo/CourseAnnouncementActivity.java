package com.example.liuteng.livedemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.liuteng.livedemo.base.BaseActivity;
import com.example.liuteng.livedemo.bean.LiveBean;

/**
 * Created by 刘腾 on 2017/3/6.
 */

public class CourseAnnouncementActivity extends BaseActivity{
    private LiveBean mCurrentLiveBean;
    @Override
    public void initParms(Bundle parms) {
        if (parms!=null){
            mCurrentLiveBean= (LiveBean) parms.get("courseInfo");
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_course_announcement;
    }

    @Override
    public void initView(View view) {
        RelativeLayout courseSignupRl=$(R.id.rl_course_signup);
        courseSignupRl.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.rl_course_signup:
                Intent intent=new Intent(this,CourseSignupActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
