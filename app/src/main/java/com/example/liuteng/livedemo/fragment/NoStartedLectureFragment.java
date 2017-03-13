package com.example.liuteng.livedemo.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bokecc.sdk.mobile.live.DWLive;
import com.bokecc.sdk.mobile.live.DWLiveLoginListener;
import com.bokecc.sdk.mobile.live.Exception.DWLiveException;
import com.bokecc.sdk.mobile.live.pojo.PublishInfo;
import com.bokecc.sdk.mobile.live.pojo.RoomInfo;
import com.bokecc.sdk.mobile.live.pojo.TemplateInfo;
import com.bokecc.sdk.mobile.live.pojo.Viewer;
import com.example.liuteng.livedemo.CourseAnnouncementActivity;
import com.example.liuteng.livedemo.LiveRoomActivity;
import com.example.liuteng.livedemo.R;
import com.example.liuteng.livedemo.SignedLecture;
import com.example.liuteng.livedemo.adapter.LiveRadioAdapter;
import com.example.liuteng.livedemo.adapter.UnStartedLectureAdapter;
import com.example.liuteng.livedemo.base.BaseFragment;
import com.example.liuteng.livedemo.bean.LectureBean;
import com.example.liuteng.livedemo.bean.LiveBean;
import com.example.liuteng.livedemo.model.LiveRecordInfo;
import com.example.liuteng.livedemo.model.MyLectureInfo;
import com.example.liuteng.livedemo.util.CommonSharePreference;
import com.example.liuteng.livedemo.util.XlfLog;
import com.example.liuteng.livedemo.view.DefaultDialog;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.List;

/**
 * Created by 刘腾 on 2017/3/12.
 */

public class NoStartedLectureFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private ProgressBar mLoading;
    private RelativeLayout mNoLecture;
    private MyLectureInfo mLectureInfo;
    private List<LectureBean> mDatas;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private UnStartedLectureAdapter mAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
    private DWLive dwLive = DWLive.getInstance();
    private DWLiveLoginListener dwLiveLoginListener = new DWLiveLoginListener() {

        @Override
        public void onLogin(TemplateInfo info, Viewer viewer, RoomInfo roomInfo, PublishInfo publishInfo) {
            Intent intent = new Intent(getActivity(), LiveRoomActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("chat", info.getChatView());
            bundle.putString("pdf", info.getPdfView());
            bundle.putString("qa", info.getQaView());
            bundle.putInt("dvr", roomInfo.getDvr());
            intent.putExtras(bundle);
            startActivity(intent);
        }

        @Override
        public void onException(DWLiveException exception) {
            XlfLog.d(exception.getMessage());
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_no_started_lecture;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews();
        initDatas();
    }

    private void initDatas() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mLectureInfo = new MyLectureInfo();
                mDatas = mLectureInfo.getUnStartedLecture();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mLoading.setVisibility(View.GONE);

                        if (mDatas == null || mDatas.size() == 0) {
                            mNoLecture.setVisibility(View.VISIBLE);
                        } else {
                            initViews();
                        }
                    }
                });
            }
        }).start();
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new UnStartedLectureAdapter(this.getContext(), mDatas);
        initHeaderAndFooter();
        initLoadMore();
        mRecyclerView.setAdapter(mLoadMoreWrapper);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                LectureBean lectureBean = mDatas.get(position - mHeaderAndFooterWrapper.getHeadersCount());
                if (lectureBean.isLiving()) {
                    moveToLiveActivity();
                } else {
                    moveToAdvanceActivity(lectureBean.getLectureId());
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void moveToAdvanceActivity(String lectureId) {
        Intent intent = new Intent(this.getContext(), CourseAnnouncementActivity.class);
        intent.putExtra("courseInfoId", lectureId);
        //TODO 临时数据，接口调试时删除
        intent.putExtra("testData", true);
        startActivity(intent);
    }

    private void moveToLiveActivity() {
        dwLive.setDWLiveLoginParams(dwLiveLoginListener, true, "D9180EE599D5BD46", "D2ACF9FDA4D4E2F79C33DC5901307461", "testAndroid", "111111");
        dwLive.startLogin();
    }

    private void initLoadMore() {
        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        mLoadMoreWrapper.setLoadMoreView(LayoutInflater.from(this.getContext()).inflate(R.layout.default_loading, mRecyclerView, false));
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                XlfLog.d("执行到加载更多");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<LectureBean> lectureBeans = mLectureInfo.getUnStartedLecture();
                        if (lectureBeans == null || lectureBeans.size() == 0) {
                            noMoreData();
                            return;
                        } else {
                            mDatas.addAll(lectureBeans);
                            if (mDatas.size() > 40) {
                                noMoreData();
                                return;
                            }
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mLoadMoreWrapper.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                }).start();
            }
        });
    }

    private void noMoreData() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(NoStartedLectureFragment.this.getContext(), "没有更多数据了", Toast.LENGTH_LONG).show();
                mLoadMoreWrapper.setLoadOver(true);
                mLoadMoreWrapper.notifyDataSetChanged();
            }
        });
    }

    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        if (CommonSharePreference.get("hasCancelUnStartHint", false)) {
            return;
        } else {
            final View view = LayoutInflater.from(this.getContext()).inflate(R.layout.unstarted_lecture_head, mRecyclerView, false);
            ImageView cancelHintTv = (ImageView) view.findViewById(R.id.tv_cacel_hint);
            cancelHintTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonSharePreference.set("hasCancelUnStartHint", true);
                    view.setVisibility(View.GONE);
                    mHeaderAndFooterWrapper.removeAllHeadView();
                    mHeaderAndFooterWrapper.notifyDataSetChanged();
                }
            });
            mHeaderAndFooterWrapper.addHeaderView(view);
        }
    }

    private void bindViews() {
        mRecyclerView = find(R.id.mRecyclerView);
        mLoading = find(R.id.pb_load);
        mNoLecture = find(R.id.rl_no_lecture);

    }
}
