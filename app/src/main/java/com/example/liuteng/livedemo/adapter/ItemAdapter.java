package com.example.liuteng.livedemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liuteng.livedemo.R;
import com.example.liuteng.livedemo.bean.LabelItem;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuteng on 2017/3/8.
 */

public class ItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<LabelItem> mLabelItemList;

    public ItemAdapter(Context mContext, List<LabelItem> mLabelItemList) {
        this.mContext = mContext;
        this.mLabelItemList = mLabelItemList;
    }

    @Override
    public int getCount() {
        return mLabelItemList == null ? 0 : mLabelItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mLabelItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.popwindow_record_item, parent, false);
            mViewHolder.tv_record_item_content = (TextView) convertView.findViewById(R.id.tv_record_item_content);
            mViewHolder.tv_record_item_select = (ImageView) convertView.findViewById(R.id.tv_record_item_select);
            mViewHolder.ll_record_item_parent = (LinearLayout) convertView.findViewById(R.id.ll_record_item_parent);
            mViewHolder.iv_record_item = (SimpleDraweeView) convertView.findViewById(R.id.iv_record_item);
            mViewHolder.view_record_item = convertView.findViewById(R.id.view_record_item);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        LabelItem labelItem = mLabelItemList.get(position);
        if (!TextUtils.isEmpty(labelItem.getImageUrl())) {
            mViewHolder.ll_record_item_parent.setVisibility(View.GONE);
            mViewHolder.iv_record_item.setVisibility(View.VISIBLE);
            mViewHolder.iv_record_item.setImageURI(labelItem.getImageUrl());
        } else {
            mViewHolder.ll_record_item_parent.setVisibility(View.VISIBLE);
            mViewHolder.iv_record_item.setVisibility(View.GONE);
            mViewHolder.tv_record_item_content.setText(labelItem.getLabelContent());
        }

        if (labelItem.isSelected()) {
            mViewHolder.view_record_item.setVisibility(View.VISIBLE);
            mViewHolder.tv_record_item_select.setVisibility(View.VISIBLE);
            mViewHolder.tv_record_item_content.setTextColor(Color.parseColor("#297beb"));
            mViewHolder.iv_record_item.setBackgroundResource(R.drawable.shape_brand_image_red_bg);

        } else {
            mViewHolder.view_record_item.setVisibility(View.GONE);
            mViewHolder.tv_record_item_select.setVisibility(View.GONE);
            mViewHolder.tv_record_item_content.setTextColor(Color.parseColor("#282828"));
            mViewHolder.iv_record_item.setBackgroundResource(0);
            mViewHolder.iv_record_item.setBackgroundResource(R.drawable.shape_brand_image_bg);
        }
        return convertView;
    }

    class ViewHolder {
        private TextView tv_record_item_content;
        private ImageView tv_record_item_select;
        private LinearLayout ll_record_item_parent;
        private SimpleDraweeView iv_record_item;
        private View view_record_item;
    }
}
