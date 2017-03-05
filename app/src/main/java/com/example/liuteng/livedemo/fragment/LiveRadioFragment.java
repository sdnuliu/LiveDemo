package com.example.liuteng.livedemo.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.liuteng.livedemo.R;
import com.example.liuteng.livedemo.adapter.LiveRadioAdapter;
import com.example.liuteng.livedemo.base.BaseFragment;
import com.example.liuteng.livedemo.bean.LiveBean;
import com.example.liuteng.livedemo.util.XlfLog;
import com.example.liuteng.livedemo.view.DefaultDialog;
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
    private List<LiveBean> mDatas = new ArrayList<>();
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;

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
        LiveBean liveBean;
        for (int i = 0; i < 10; i++) {
            liveBean = new LiveBean();
            liveBean.setType(1);
            liveBean.setPlayTimes(i + "");
            mDatas.add(liveBean);
        }
        mLoading.setVisibility(View.GONE);
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
                showMobileDialog();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void showMobileDialog() {
        final DefaultDialog.Builder builder = new DefaultDialog.Builder(this.getContext());
        builder.setTitle("请输入您报名时预留的手机号");
        View defaultView=LayoutInflater.from(this.getContext()).inflate(R.layout.default_dialog,null);
        final EditText telEt= (EditText) defaultView.findViewById(R.id.et_tel);
        builder.setView(defaultView);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(LiveRadioFragment.this.getContext(), "取消被点击了", Toast.LENGTH_LONG).show();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               checkTel(telEt);
            }
        });
        new DefaultDialog(builder).show();
    }

    private void checkTel(EditText telEt) {
        if (TextUtils.isEmpty(telEt.getText().toString())){
            Toast.makeText(this.getContext(),"手机号不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        ProgressDialog dialog = new ProgressDialog(LiveRadioFragment.this.getContext());
        dialog.setMessage("验证中，请稍后");
        dialog.show();
        
    }

    private void initLoadMore() {
        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        mLoadMoreWrapper.setLoadMoreView(LayoutInflater.from(this.getContext()).inflate(R.layout.default_loading, mRecyclerView, false));
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                XlfLog.d("执行到加载更多");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mDatas.size() > 40) {
                            Toast.makeText(LiveRadioFragment.this.getContext(), "没有更多数据了", Toast.LENGTH_LONG).show();
                            mLoadMoreWrapper.setLoadOver(true);
                            mLoadMoreWrapper.notifyDataSetChanged();
                            return;
                        }
                        LiveBean liveBean;
                        int m = new Random().nextInt(10);
                        for (int i = m; i < m + 10; i++) {
                            liveBean = new LiveBean();
                            liveBean.setType(1);
                            liveBean.setPlayTimes(i + "");
                            mDatas.add(liveBean);
                        }
                        mLoadMoreWrapper.notifyDataSetChanged();
                    }
                }, 1500);
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
