package com.example.liuteng.livedemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liuteng.livedemo.R;
import com.example.liuteng.livedemo.adapter.LiveRadioAdapter;
import com.example.liuteng.livedemo.base.BaseFragment;
import com.example.liuteng.livedemo.bean.LiveBean;
import com.example.liuteng.livedemo.model.LiveRecordInfo;
import com.example.liuteng.livedemo.util.XlfLog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 直播Fragment
 */

public class LiveRadioFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private TextView mLoading;
    private LiveRadioAdapter mAdapter;
    private List<LiveBean> mDatas;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
    private Handler mhandler = new Handler(Looper.getMainLooper());
    private LiveRecordInfo mLiveRecordInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_list;
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
                mLiveRecordInfo = new LiveRecordInfo();
                mDatas = mLiveRecordInfo.getLiveData();
                mhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mDatas == null || mDatas.size() == 0) {
                            mLoading.setVisibility(View.VISIBLE);
                            mLoading.setText("没有直播内容");
                        } else {
                            mLoading.setVisibility(View.GONE);
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
        mAdapter = new LiveRadioAdapter(this.getContext(), mDatas);
        initHeaderAndFooter();
        initLoadMore();
        mRecyclerView.setAdapter(mLoadMoreWrapper);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
               LiveBean liveBean=mDatas.get(position-1);
                if (liveBean.getLiveMeetingBean().getMeetingType()==0){
                    prepareMoveToLiveActivity();
                }else{
                    prepareMoveToAdvanceActivity();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void prepareMoveToAdvanceActivity() {
    }

    private void prepareMoveToLiveActivity() {

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
                        List<LiveBean> liveBeens = mLiveRecordInfo.getLiveData();
                        if (liveBeens == null || liveBeens.size() == 0) {
                            noMoreData();
                            return;
                        } else {
                            mDatas.addAll(liveBeens);
                            if (mDatas.size() > 40) {
                                noMoreData();
                                return;
                            }
                            mhandler.post(new Runnable() {
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
        mhandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LiveRadioFragment.this.getContext(), "没有更多数据了", Toast.LENGTH_LONG).show();
                mLoadMoreWrapper.setLoadOver(true);
                mLoadMoreWrapper.notifyDataSetChanged();
            }
        });
    }

    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.live_head, mRecyclerView, false);
        mHeaderAndFooterWrapper.addHeaderView(view);
    }

    private void bindViews() {
        mRecyclerView = find(R.id.mRecyclerView);
        mLoading = find(R.id.tv_load);
    }

}
