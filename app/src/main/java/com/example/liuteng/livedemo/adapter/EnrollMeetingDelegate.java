package com.example.liuteng.livedemo.adapter;

import android.text.TextUtils;

import com.example.liuteng.livedemo.R;
import com.example.liuteng.livedemo.bean.LiveBean;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by liuteng on 2017/3/1.
 */
public class EnrollMeetingDelegate implements com.zhy.adapter.recyclerview.base.ItemViewDelegate<com.example.liuteng.livedemo.bean.LiveBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_live_enroll_meeting;
    }

    @Override
    public boolean isForViewType(LiveBean item, int position) {
        return item.getType() == 2;
    }

    @Override
    public void convert(ViewHolder holder, LiveBean liveBean, int position) {
        if (TextUtils.isEmpty(liveBean.getTitle())) {
            holder.setVisible(R.id.ll_live_item, false);
            holder.setVisible(R.id.tv_no_meeting, true);
        } else {
            holder.setVisible(R.id.ll_live_item, true);
            holder.setVisible(R.id.tv_no_meeting, false);
        }
    }
}
