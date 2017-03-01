package com.example.liuteng.livedemo.adapter;

import com.example.liuteng.livedemo.R;
import com.example.liuteng.livedemo.bean.LiveBean;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by liuteng on 2017/3/1.
 */
public class TitleDelegate implements com.zhy.adapter.recyclerview.base.ItemViewDelegate<LiveBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_title;
    }

    @Override
    public boolean isForViewType(LiveBean item, int position) {
        return item.getType() == 0;
    }

    @Override
    public void convert(ViewHolder holder, LiveBean liveBean, int position) {
        holder.setText(R.id.tv_live_title, liveBean.getTitle());
    }
}
