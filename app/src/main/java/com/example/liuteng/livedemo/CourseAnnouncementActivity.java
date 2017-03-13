package com.example.liuteng.livedemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.liuteng.livedemo.base.BaseActivity;
import com.example.liuteng.livedemo.bean.LiveBean;

/**
 * Created by 刘腾 on 2017/3/6.
 */

public class CourseAnnouncementActivity extends BaseActivity {
    private String mCourseId;
    private boolean isSignUp=false;

    @Override
    public void initParms(Bundle parms) {
        if (parms != null) {
            mCourseId = (String) parms.getString("courseInfoId");
            isSignUp=(boolean)parms.getBoolean("testData");
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_course_announcement;
    }

    @Override
    public void initView(View view) {
        RelativeLayout courseSignupRl = $(R.id.rl_course_signup);
        TextView signUpTv=$(R.id.tv_go_signup);
        courseSignupRl.setOnClickListener(this);
        if (isSignUp){
            signUpTv.setText("已报名");
            courseSignupRl.setBackgroundColor(Color.DKGRAY);
        }else{
            signUpTv.setText("未报名");
            courseSignupRl.setBackgroundResource(R.color.colorPrimary);
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.rl_course_signup:
                Intent intent = new Intent(this, CourseSignupActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
