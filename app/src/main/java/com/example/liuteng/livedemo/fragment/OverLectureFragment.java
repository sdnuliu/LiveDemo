package com.example.liuteng.livedemo.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.liuteng.livedemo.CourseAnnouncementActivity;
import com.example.liuteng.livedemo.R;
import com.example.liuteng.livedemo.RecrodDetailActivity;
import com.example.liuteng.livedemo.adapter.UnStartedLectureAdapter;
import com.example.liuteng.livedemo.base.BaseFragment;
import com.example.liuteng.livedemo.bean.EndLectureBean;
import com.example.liuteng.livedemo.bean.LectureBean;
import com.example.liuteng.livedemo.bean.RecordBean;
import com.example.liuteng.livedemo.model.MyLectureInfo;
import com.example.liuteng.livedemo.util.CommonSharePreference;
import com.example.liuteng.livedemo.util.DateUtil;
import com.example.liuteng.livedemo.util.XlfLog;
import com.example.liuteng.livedemo.view.DefaultDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.List;

import static com.example.liuteng.livedemo.R.id.mRecyclerView;

/**
 * Created by 刘腾 on 2017/3/12.
 */

public class OverLectureFragment extends BaseFragment {
    private MyLectureInfo mLectureInfo;
    private List<EndLectureBean> mDatas;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private RecyclerView mRecyclerView;
    private ProgressBar mLoading;
    private RelativeLayout mNoLecture;
    private CommonAdapter mAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
    private DefaultDialog.Builder builder;
    private AlertDialog alertDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_over_lecture;
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
                mDatas = mLectureInfo.getEndLecture();
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
        mAdapter = new CommonAdapter<EndLectureBean>(this.getContext(), R.layout.item_end_lecture, mDatas) {
            @Override
            protected void convert(ViewHolder holder, EndLectureBean s, int position) {
                holder.setText(R.id.tv_end_title, s.getTitle());
                holder.setTextColor(R.id.tv_end_lecture_status, Color.GRAY);
                if (s.getStatus() == 0) {
                    holder.setText(R.id.tv_end_lecture_status, "未参加");
                } else if (s.getStatus() == 1) {
                    holder.setText(R.id.tv_end_lecture_status, "已参加");
                }
                holder.setText(R.id.tv_end_lecture_publish_time, DateUtil.longToString(s.getPublishDate()));
                ((SimpleDraweeView) holder.getView(R.id.sdv_end_lecture_pic)).setImageURI(Uri.parse(s.getPreviewPic()));
            }
        };
        initHeaderAndFooter();
        initLoadMore();
        mRecyclerView.setAdapter(mLoadMoreWrapper);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                XlfLog.d("点击位置：" + position + "头部的个数：" + mHeaderAndFooterWrapper.getHeadersCount());
                EndLectureBean lectureBean = mDatas.get(position - mHeaderAndFooterWrapper.getHeadersCount());
                if (lectureBean.isAllowRewatch()) {
                    moveToRecordActivity(lectureBean);
                } else {
                    showDialog();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void showDialog() {
        builder = new DefaultDialog.Builder(this.getContext());
        builder.setTitle("很遗憾");
        builder.setMessage("该视频无法回放，请准时参加直播！");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (alertDialog.isShowing())
                    alertDialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (alertDialog.isShowing())
                    alertDialog.dismiss();
            }
        });
        alertDialog = new DefaultDialog(builder).show();
    }

    private void moveToRecordActivity(EndLectureBean lectureBean) {
        Intent intent = new Intent(this.getContext(), RecrodDetailActivity.class);
        intent.putExtra("recordId", "4ECB37B431858C689C33DC5901307461");
        startActivity(intent);
    }

    private void initLoadMore() {
        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        mLoadMoreWrapper.setLoadMoreView(LayoutInflater.from(this.getContext()).inflate(R.layout.default_loading,
                mRecyclerView, false));
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                XlfLog.d("执行到加载更多");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<EndLectureBean> lectureBeans = mLectureInfo.getEndLecture();
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
                Toast.makeText(OverLectureFragment.this.getContext(), "没有更多数据了", Toast.LENGTH_LONG).show();
                mLoadMoreWrapper.setLoadOver(true);
                mLoadMoreWrapper.notifyDataSetChanged();
            }
        });
    }

    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        if (CommonSharePreference.get("hasCancelEndHint", false)) {
            return;
        } else {
            final View view = LayoutInflater.from(this.getContext()).inflate(R.layout.end_lecture_head,
                    mRecyclerView, false);
            final RelativeLayout rlContainer = (RelativeLayout) view.findViewById(R.id.rl_container);
            ImageView cancelHintTv = (ImageView) view.findViewById(R.id.tv_cacel_hint);
            cancelHintTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonSharePreference.set("hasCancelEndHint", true);
                    rlContainer.setVisibility(View.GONE);
                }
            });
            mHeaderAndFooterWrapper.addHeaderView(view);
        }
    }

    private void bindViews() {
        mRecyclerView = find(R.id.rv_end_lecture);
        mLoading = find(R.id.pb_load);
        mNoLecture = find(R.id.rl_no_lecture);

    }
}
