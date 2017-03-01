package com.example.liuteng.livedemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.liuteng.livedemo.R;
import com.example.liuteng.livedemo.base.BaseFragment;
import com.example.liuteng.livedemo.util.XlfLog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuteng on 2017/2/28.
 */
public class RecordRadioFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private TextView mLoading;
    private CommonAdapter<String> mAdapter;
    private List<String> mDatas = new ArrayList<>();
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_list;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews();
        initDatas();
        initViews();

    }

    private void initDatas() {
        XlfLog.d("视频数据初始化");
        for (int i = 'A'; i <= 'z'; i++) {
            mDatas.add((char) i + "");
        }
        mLoading.setVisibility(View.GONE);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new CommonAdapter<String>(this.getContext(), R.layout.item_list, mDatas) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_record_play_times, s + "次");
            }
        };
        initHeaderAndFooter();
        mRecyclerView.setAdapter(mHeaderAndFooterWrapper);
    }

    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        View view= LayoutInflater.from(this.getContext()).inflate(R.layout.record_head,mRecyclerView,false);
        mHeaderAndFooterWrapper.addHeaderView(view);
    }

    private void bindViews() {
        mRecyclerView = find(R.id.mRecyclerView);
        mLoading = find(R.id.tv_load);
    }
}
