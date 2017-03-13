package com.example.liuteng.livedemo.adapter;

import android.graphics.Color;
import android.net.Uri;

import com.example.liuteng.livedemo.R;
import com.example.liuteng.livedemo.bean.LectureBean;
import com.example.liuteng.livedemo.util.DateUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by liuteng on 2017/3/13.
 */

class UnstartedLectureDelegate implements com.zhy.adapter.recyclerview.base.ItemViewDelegate<LectureBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_unstarted_lecture;
    }

    @Override
    public boolean isForViewType(LectureBean item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, LectureBean lectureBean, int position) {
        holder.setText(R.id.tv_no_started_title, lectureBean.getTitle());
        if (lectureBean.isLiving()) {
            holder.setTextColor(R.id.tv_no_started_lecture_status, Color.RED);
            holder.setText(R.id.tv_no_started_lecture_status, "正在直播");
        } else {
            holder.setTextColor(R.id.tv_no_started_lecture_status, Color.GRAY);
            if (lectureBean.getStatue() == 0) {
                holder.setText(R.id.tv_no_started_lecture_status, "已审核");
            } else if (lectureBean.getStatue() == 1) {
                holder.setText(R.id.tv_no_started_lecture_status, "未审核");
            }

        }
        holder.setText(R.id.tv_no_started_lecture_publish_time, DateUtil.longToString(lectureBean.getDate()));
        if (lectureBean.getLiveMeetingBean().getMeetingType() == 1) {
            holder.setText(R.id.tv_no_started_teacher, lectureBean.getLiveMeetingBean().getLector());
        } else {
            holder.setText(R.id.tv_no_started_teacher, "讲师:" + lectureBean.getLiveMeetingBean().getLector());
        }
        ((SimpleDraweeView) holder.getView(R.id.sdv_no_started_lecture_pic)).setImageURI(Uri.parse(lectureBean.getPic()));
    }
}
