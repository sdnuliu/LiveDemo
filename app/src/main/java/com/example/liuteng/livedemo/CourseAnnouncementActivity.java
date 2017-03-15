package com.example.liuteng.livedemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.liuteng.livedemo.base.BaseActivity;
import com.example.liuteng.livedemo.bean.CourseDetailBean;
import com.example.liuteng.livedemo.bean.LiveBean;
import com.example.liuteng.livedemo.model.CourseInfo;
import com.example.liuteng.livedemo.model.ResListener;
import com.example.liuteng.livedemo.util.DateUtil;
import com.example.liuteng.livedemo.view.TitleView;

import java.text.ParseException;
import java.util.List;

/**
 * Created by 刘腾 on 2017/3/6.
 */

public class CourseAnnouncementActivity extends BaseActivity {
    private String mCourseId;
    private boolean isSignUp = false;
    private RelativeLayout courseContainer;
    private ProgressBar mLoadingPb;
    private CourseInfo courseInfo;
    private TextView title;
    private TextView coureseDate;
    private TextView courseType;
    private TextView courseTeacher;
    private TextView coursePopular;
    private TextView courseDetail;
    private TextView courseContent;
    private LinearLayout childMeetLl;
    private LayoutInflater xInflater;
    private View child_view;
    private CourseDetailBean.PartMeetingBean meetinBean;

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
        childMeetLl = $(R.id.ll_container);
        TextView signUpTv = $(R.id.tv_go_signup);
        courseSignupRl.setOnClickListener(this);
        if (isSignUp) {
            signUpTv.setText("已报名");
            courseSignupRl.setBackgroundColor(Color.DKGRAY);
        } else {
            signUpTv.setText("未报名");
            courseSignupRl.setBackgroundResource(R.color.colorPrimary);
        }
        xInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void doBusiness(Context mContext) {
        courseInfo = new CourseInfo();
        courseInfo.getCouserDetail("2233", new ResListener<CourseDetailBean>() {
            @Override
            public void onSuccess(CourseDetailBean courseDetailBean) {
                mLoadingPb.setVisibility(View.GONE);
                courseContainer.setVisibility(View.VISIBLE);
                initPageInfo(courseDetailBean);
            }

            @Override
            public void failed(String message) {
                mLoadingPb.setVisibility(View.GONE);
                courseContainer.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initPageInfo(CourseDetailBean courseDetailBean) {
        title.setText(courseDetailBean.getTitle());
        meetinBean = courseDetailBean.getMeetinBean();
        try {
            coureseDate.setText("时间：" + DateUtil.longToString(meetinBean.getStartTime()) +
                    "-" + DateUtil.longToString(meetinBean.getEndTime(), "HH:mm"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (meetinBean.getType()) {
            case 1:
                courseType.setText("厂商会议");
                break;
            case 2:
                courseType.setText("专家会议");
                break;
            case 3:
                courseType.setText("研讨会");
                break;
            default:
                break;
        }
        if (!TextUtils.isEmpty(meetinBean.getLecture())) {
            courseTeacher.setText("讲师：" + meetinBean.getLecture());
        }
        coursePopular.setText("人气：" + meetinBean.getPopular());
        if (!TextUtils.isEmpty(meetinBean.getLectureInfo())) {
            courseDetail.setVisibility(View.VISIBLE);
            courseDetail.setText(meetinBean.getLectureInfo());
        }
        courseContent.setText(Html.fromHtml(courseDetailBean.getContent()));
        List<CourseDetailBean.PartContentBean> partList = courseDetailBean.getPartList();
        if (partList != null && partList.size() > 0) {
            for (CourseDetailBean.PartContentBean childBean : partList) {
                child_view = xInflater.inflate(R.layout.child_meeting_layout, null);
                TextView childTtile = (TextView) child_view.findViewById(R.id.tv_child_title);
                TextView childTeacher = (TextView) child_view.findViewById(R.id.tv_child_teacher);
                TextView childDate = (TextView) child_view.findViewById(R.id.tv_child_date);
                childTtile.setText(childBean.getTitle());
                childTeacher.setText(childBean.getLecture());
                childDate.setText(DateUtil.longToString(childBean.getStartTime()));
                childMeetLl.addView(child_view);
            }
        }
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.rl_course_signup:
                Intent intent = new Intent(this, CourseSignupActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", title.getText().toString());
                bundle.putString("type", courseType.getText().toString());
                bundle.putString("date", coureseDate.getText().toString());
                bundle.putString("teacher", courseTeacher.getText().toString());
                bundle.putString("popular", coursePopular.getText().toString());
                intent.putExtra("courseInfo", bundle);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
