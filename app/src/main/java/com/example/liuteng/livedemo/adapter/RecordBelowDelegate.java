package com.example.liuteng.livedemo.adapter;

import com.example.liuteng.livedemo.R;
import com.example.liuteng.livedemo.bean.RecordBean;
import com.example.liuteng.livedemo.util.DateUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by liuteng on 2017/3/9.
 */
public class RecordBelowDelegate implements com.zhy.adapter.recyclerview.base.ItemViewDelegate<RecordBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.record_below_item;
    }

    @Override
    public boolean isForViewType(RecordBean item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, RecordBean recordBean, int position) {
        holder.setText(R.id.tv_record_below_title, recordBean.getTitle());
        holder.setText(R.id.tv_record_publish_below_time, DateUtil.longToString(recordBean.getPublishDate()));
        ((SimpleDraweeView) holder.getView(R.id.sdv_record_below_pic)).setImageURI(recordBean.getPreviewPic());
    }
}
