package com.example.liuteng.livedemo;

import android.content.Context;

import com.example.liuteng.livedemo.adapter.RecentMeetingDelegate;
import com.example.liuteng.livedemo.adapter.RecordBelowDelegate;
import com.example.liuteng.livedemo.bean.LiveBean;
import com.example.liuteng.livedemo.bean.RecordBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by liuteng on 2017/3/9.
 */

class RecordBelowAdapter extends MultiItemTypeAdapter<RecordBean> {
    public RecordBelowAdapter(Context context, List<RecordBean> datas) {
        super(context, datas);
        addItemViewDelegate(new RecordBelowDelegate());
    }
}
