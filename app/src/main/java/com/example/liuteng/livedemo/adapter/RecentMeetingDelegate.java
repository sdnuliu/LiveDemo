package com.example.liuteng.livedemo.adapter;

import android.graphics.Color;
import android.net.Uri;

import com.example.liuteng.livedemo.R;
import com.example.liuteng.livedemo.bean.LiveBean;
import com.example.liuteng.livedemo.util.DateUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.w3c.dom.Text;

import java.util.Date;

/**
 * Created by liuteng on 2017/3/1.
 */
public class RecentMeetingDelegate implements com.zhy.adapter.recyclerview.base.ItemViewDelegate<com.example.liuteng.livedemo.bean.LiveBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_live_recent_meeting;
    }

    @Override
    public boolean isForViewType(LiveBean item, int position) {
        return item.getType() == 1;
    }

    @Override
    public void convert(ViewHolder holder, LiveBean liveBean, int position) {
        holder.setText(R.id.tv_live_title, liveBean.getTitle());
        if (liveBean.isLiving()) {
            holder.setTextColor(R.id.tv_live_limit, Color.RED);
            holder.setText(R.id.tv_live_limit, "正在直播");
        } else {
            holder.setTextColor(R.id.tv_live_limit, Color.GRAY);
            holder.setText(R.id.tv_live_limit, "人气：" + liveBean.getPopularity());
        }
        holder.setText(R.id.tv_live_date, DateUtil.longToString(liveBean.getDate()));
        if (liveBean.getLiveMeetingBean().getMeetingType() == 1) {
            holder.setText(R.id.tv_live_lector, liveBean.getLiveMeetingBean().getLector());
        } else {
            holder.setText(R.id.tv_live_lector, "讲师:" + liveBean.getLiveMeetingBean().getLector());
        }
        ((SimpleDraweeView) holder.getView(R.id.iv_live_pic)).setImageURI(Uri.parse(liveBean.getPic()));
    }
}
