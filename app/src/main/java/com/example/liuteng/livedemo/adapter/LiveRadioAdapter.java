package com.example.liuteng.livedemo.adapter;

import android.content.Context;

import com.example.liuteng.livedemo.bean.LiveBean;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by liuteng on 2017/3/1.
 */

public class LiveRadioAdapter extends MultiItemTypeAdapter<LiveBean> {

    public LiveRadioAdapter(Context context, List<LiveBean> datas) {
        super(context, datas);
        addItemViewDelegate(new RecentMeetingDelegate());
    }
}
