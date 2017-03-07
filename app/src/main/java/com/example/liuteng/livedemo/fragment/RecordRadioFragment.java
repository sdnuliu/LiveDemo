package com.example.liuteng.livedemo.fragment;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liuteng.livedemo.R;
import com.example.liuteng.livedemo.base.BaseFragment;
import com.example.liuteng.livedemo.bean.LabelInfo;
import com.example.liuteng.livedemo.bean.RecordBean;
import com.example.liuteng.livedemo.model.LiveRecordInfo;
import com.example.liuteng.livedemo.util.DateUtil;
import com.example.liuteng.livedemo.util.XlfLog;
import com.example.liuteng.livedemo.view.DropdownPopupWindow;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuteng on 2017/2/28.
 */
public class RecordRadioFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private TextView mLoading;
    private CommonAdapter<RecordBean> mAdapter;
    private List<RecordBean> mDatas = new ArrayList<>();
    private LiveRecordInfo mRecordInfo;
    private android.os.Handler mhandler = new android.os.Handler(Looper.getMainLooper());
    private LoadMoreWrapper mLoadMoreWrapper;
    private List<LabelInfo> labelInfos;
    private LinearLayout mInstrumentSelectLl;
    private LinearLayout mIndustrySelectLl;
    private LinearLayout mExpertSelectLl;
    private LinearLayout mFirmsSelectLl;
    private LinearLayout mRecordSelectLl;
    private DropdownPopupWindow dropDownPopup;
    //    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_reocrd_list;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews();
        initDatas();

    }

    private void initDatas() {
        XlfLog.d("视频数据初始化");
        new Thread(new Runnable() {
            @Override
            public void run() {
                mRecordInfo = new LiveRecordInfo();
                mDatas = mRecordInfo.getRecrodData();
                labelInfos = mRecordInfo.getLabelData();
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
        mAdapter = new CommonAdapter<RecordBean>(this.getContext(), R.layout.item_list, mDatas) {
            @Override
            protected void convert(ViewHolder holder, RecordBean s, int position) {
                holder.setText(R.id.tv_record_title, s.getTitle());
                holder.setText(R.id.tv_record_publish_time, DateUtil.longToString(s.getPublishDate()));
                holder.setText(R.id.tv_record_play_times, s.getPlayTimes() + "次");
                ((SimpleDraweeView) holder.getView(R.id.sdv_record_pic)).setImageURI(s.getPreviewPic());
            }
        };
//        initHeaderAndFooter();
        initLoadMore();
        mRecyclerView.setAdapter(mLoadMoreWrapper);
    }

    private void initLoadMore() {
        mLoadMoreWrapper = new LoadMoreWrapper(mAdapter);
        mLoadMoreWrapper.setLoadMoreView(LayoutInflater.from(this.getContext()).inflate(R.layout.default_loading, mRecyclerView, false));
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                XlfLog.d("执行到加载更多");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<RecordBean> recordBeans = mRecordInfo.getRecrodData();
                        if (recordBeans == null || recordBeans.size() == 0) {
                            noMoreData();
                            return;
                        } else {
                            mDatas.addAll(recordBeans);
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
                Toast.makeText(RecordRadioFragment.this.getContext(), "没有更多数据了", Toast.LENGTH_LONG).show();
                mLoadMoreWrapper.setLoadOver(true);
                mLoadMoreWrapper.notifyDataSetChanged();
            }
        });
    }

//    private void initHeaderAndFooter() {
//        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
//        View view= LayoutInflater.from(this.getContext()).inflate(R.layout.record_head,mRecyclerView,false);
//        mHeaderAndFooterWrapper.addHeaderView(view);
//    }

    private void bindViews() {
        mRecyclerView = find(R.id.mRecyclerView);
        mLoading = find(R.id.tv_load);
        mInstrumentSelectLl = find(R.id.ll_record_instrument_select);
        mIndustrySelectLl = find(R.id.ll_record_industry_select);
        mExpertSelectLl = find(R.id.ll_record_expert_select);
        mFirmsSelectLl = find(R.id.ll_record_firms_select);
        mRecordSelectLl = find(R.id.ll_record_select_title);
        mInstrumentSelectLl.setOnClickListener(this);
        mIndustrySelectLl.setOnClickListener(this);
        mExpertSelectLl.setOnClickListener(this);
        mFirmsSelectLl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_record_instrument_select:
                LabelInfo instruments = labelInfos.get(0);
                showPopWindow(instruments);
                break;
        }
    }

    private void showPopWindow(LabelInfo labelInfo) {
        if (labelInfo.getLabelItemList() == null || labelInfo.getLabelItemList().size() == 0) {
            noLabelInfo();
        } else if (dropDownPopup != null && dropDownPopup.isShowing()) {
            XlfLog.d("弹出框消失");
            dropDownPopup.dismiss();
            dropDownPopup = null;
        } else {
            XlfLog.d("该分类下有信息");
            dropDownPopup = new DropdownPopupWindow(this.getContext(), mRecordSelectLl, labelInfo.getLabelItemList());
            dropDownPopup.showAsDropDown(mRecordSelectLl, 0, 0);
        }
    }

    private void noLabelInfo() {
        Snackbar.make(mRecyclerView, "该分类下没有信息", Snackbar.LENGTH_LONG).show();
    }
}
