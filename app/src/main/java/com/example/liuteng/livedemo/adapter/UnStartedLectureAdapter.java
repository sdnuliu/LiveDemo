package com.example.liuteng.livedemo.adapter;

import android.content.Context;

import com.example.liuteng.livedemo.bean.LectureBean;
import com.example.liuteng.livedemo.bean.LiveBean;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by liuteng on 2017/3/13.
 */

public class UnStartedLectureAdapter extends MultiItemTypeAdapter<LectureBean> {

    public UnStartedLectureAdapter(Context context, List<LectureBean> datas) {
        super(context, datas);
        addItemViewDelegate(new UnstartedLectureDelegate());
    }
}
