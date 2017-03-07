package com.example.liuteng.livedemo.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
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
import com.example.liuteng.livedemo.adapter.LiveRadioAdapter;
import com.example.liuteng.livedemo.base.BaseFragment;
import com.example.liuteng.livedemo.bean.LiveBean;
import com.example.liuteng.livedemo.model.LiveRecordInfo;
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
    private List<LiveBean> mDatas;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
    private Handler mhandler = new Handler(Looper.getMainLooper());
    private LiveRecordInfo mLiveRecordInfo;
    private DefaultDialog.Builder builder;
    private DWLive dwLive = DWLive.getInstance();
    private AlertDialog alertDialog;
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
                LiveBean liveBean = mDatas.get(position - 1);
                if (liveBean.isLiving()) {
                    prepareMoveToLiveActivity();
                } else {
                    prepareMoveToAdvanceActivity(liveBean);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }


    private void prepareMoveToAdvanceActivity(LiveBean liveBean) {
        Intent intent=new Intent(this.getContext(),CourseAnnouncementActivity.class);
        intent.putExtra("courseInfo",liveBean);
        startActivity(intent);
    }

    private void prepareMoveToLiveActivity() {
        showMobileDialog();
    }

    private void showMobileDialog() {
        builder = new DefaultDialog.Builder(this.getContext());
        builder.setTitle("请输入您报名时预留的手机号");
        View defaultView = LayoutInflater.from(this.getContext()).inflate(R.layout.default_dialog, null);
        final EditText telEt = (EditText) defaultView.findViewById(R.id.et_tel);
        builder.setView(defaultView);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (alertDialog.isShowing())
                    alertDialog.dismiss();
                Toast.makeText(LiveRadioFragment.this.getContext(), "取消被点击了", Toast.LENGTH_LONG).show();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (alertDialog.isShowing())
                    alertDialog.dismiss();
                checkTel(telEt);
            }
        });
        alertDialog = new DefaultDialog(builder).show();
    }

    private void checkTel(final EditText telEt) {
        if (TextUtils.isEmpty(telEt.getText().toString())) {
            Toast.makeText(this.getContext(), "手机号不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        final ProgressDialog dialog = new ProgressDialog(LiveRadioFragment.this.getContext());
        dialog.setMessage("验证中，请稍后");
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isSignUp = mLiveRecordInfo.checkTel(telEt.getText().toString());
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (isSignUp) {
                    dwLive.setDWLiveLoginParams(dwLiveLoginListener, true, "D9180EE599D5BD46", "D2ACF9FDA4D4E2F79C33DC5901307461", "testAndroid","111111");
                    dwLive.startLogin();
                } else {
                    mhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            showUnSignUpDialog();
                        }
                    });
                }
            }
        }).start();

    }

    private void showUnSignUpDialog() {
        builder = new DefaultDialog.Builder(this.getContext());
        builder.setTitle("抱歉");
        builder.setMessage("抱歉！您未报名本次直播，无法进入会场，建议提前报名获取更多学习机会！");
        builder.setPositiveButton("确定", null);
        alertDialog = new DefaultDialog(builder).show();
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
