package com.example.liuteng.livedemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.liuteng.livedemo.base.BaseActivity;
import com.example.liuteng.livedemo.bean.CourseDetailBean;
import com.example.liuteng.livedemo.bean.LiveBean;
import com.example.liuteng.livedemo.model.CourseInfo;
import com.example.liuteng.livedemo.util.DateUtil;
import com.example.liuteng.livedemo.view.TitleView;

/**
 * Created by 刘腾 on 2017/3/6.
 */

public class CourseAnnouncementActivity extends BaseActivity {
    private String mCourseId;
    private boolean isSignUp = false;
    private RelativeLayout courseContainer;
    private ProgressBar mLoadingPb;
    private CourseInfo courseInfo;
    private CourseDetailBean detailBean;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private TextView title;
    private TextView coureseDate;
    private TextView courseType;
    private TextView courseTeacher;
    private TextView coursePopular;
    private TextView courseDetail;
    private TextView courseContent;

    @Override
    public void initParms(Bundle parms) {
        if (parms != null) {
            mCourseId = (String) parms.getString("courseInfoId");
            isSignUp = (boolean) parms.getBoolean("testData");
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_course_announcement;
    }

    @Override
    public void initView(View view) {
        TitleView titleView = $(R.id.rl_course_title);
        titleView.title.setText("讲座详情");
        RelativeLayout courseSignupRl = $(R.id.rl_course_signup);
        courseContainer = $(R.id.rl_course_container);
        mLoadingPb = $(R.id.pb_loading);
        title = $(R.id.tv_course_title);
        coureseDate = $(R.id.tv_course_date);
        courseType = $(R.id.tv_course_type);
        courseTeacher = $(R.id.tv_course_teacher);
        coursePopular = $(R.id.tv_course_popular);
        courseDetail = $(R.id.tv_course_teacher_detail);
        courseContent = $(R.id.tv_course_content);
        TextView signUpTv = $(R.id.tv_go_signup);
        courseSignupRl.setOnClickListener(this);
        if (isSignUp) {
            signUpTv.setText("已报名");
            courseSignupRl.setBackgroundColor(Color.DKGRAY);
        } else {
            signUpTv.setText("未报名");
            courseSignupRl.setBackgroundResource(R.color.colorPrimary);
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        courseInfo = new CourseInfo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                detailBean = courseInfo.getCouserDetail();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingPb.setVisibility(View.GONE);
                        courseContainer.setVisibility(View.VISIBLE);
                        if (detailBean != null) {
                            title.setText(detailBean.getTitle());
                            coureseDate.setText(DateUtil.longToString(detailBean.getTime()));
                            courseType.setText(detailBean.getType());
                            courseTeacher.setText("讲师：" + detailBean.getLecture());
                            coursePopular.setText("人气：" + detailBean.getPopular());
                            courseDetail.setText(detailBean.getLectureInfo());
                            courseContent.setText(detailBean.getContent());
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.rl_course_signup:
                Intent intent = new Intent(this, CourseSignupActivity.class);
                intent.putExtra("courseInfo",detailBean);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
