package com.example.liuteng.livedemo.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.liuteng.livedemo.R;
import com.example.liuteng.livedemo.bean.LabelItem;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liuteng on 2017/3/7.
 */

public class DropdownPopupWindow extends PopupWindow implements View.OnClickListener {
    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<LabelItem> mLabelItemList;
    private final View rootView;
    private TextView resetTv;
    private TextView sureTv;
    private View dismissView;
    private GridView gridView;

    public DropdownPopupWindow(Context context, LinearLayout mRecordSelectLl) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        rootView = mLayoutInflater.inflate(R.layout.popwindow_record_select, mRecordSelectLl, false);
        init();
    }

    private void init() {
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        update();
        setContentView(rootView);
        rootView.setFocusable(true);
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        resetTv = (TextView) rootView.findViewById(R.id.yqz_reset_button_popwindow_special_performance);
        sureTv = (TextView) rootView.findViewById(R.id.yqz_sure_button_popwindow_special_performance);
        dismissView = rootView.findViewById(R.id.pop_view_dismiss);

        resetTv.setOnClickListener(this);
        sureTv.setOnClickListener(this);
        dismissView.setOnClickListener(this);
        gridView = (GridView) rootView.findViewById(R.id.gridview_popwindow_special_performance);

    }
    public void setData(List<LabelItem> mLabelItemList){
        this.mLabelItemList=mLabelItemList;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pop_view_dismiss:
                dismiss();
                break;
        }
    }
}
