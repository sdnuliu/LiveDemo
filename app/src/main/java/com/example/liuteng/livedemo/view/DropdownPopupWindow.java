package com.example.liuteng.livedemo.view;

import android.content.Context;
import android.graphics.Rect;
import android.nfc.cardemulation.OffHostApduService;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.liuteng.livedemo.R;
import com.example.liuteng.livedemo.adapter.ItemAdapter;
import com.example.liuteng.livedemo.bean.LabelInfo;
import com.example.liuteng.livedemo.bean.LabelItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuteng on 2017/3/7.
 */

public class DropdownPopupWindow extends PopupWindow implements View.OnClickListener {
    private Context context;
    private LayoutInflater mLayoutInflater;
    private LabelInfo labelInfo;
    private List<LabelItem> mLabelItemList = new ArrayList<>();
    private final View rootView;
    private TextView resetTv;
    private TextView sureTv;
    private View dismissView;
    private GridView gridView;
    private ItemAdapter adapter;
    private OnSureClickLister listener;
    private LabelItem selectedLabelItem;

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
        adapter = new ItemAdapter(context, mLabelItemList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedLabelItem = mLabelItemList.get(position);
                for (int i = 0; i < mLabelItemList.size(); i++) {
                    LabelItem item = mLabelItemList.get(i);
                    if (i == position) {
                        item.setSelected(!item.isSelected());
                    } else {
                        item.setSelected(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void setData(LabelInfo labelInfo) {
        this.labelInfo = labelInfo;
        if (this.mLabelItemList != null) {
            this.mLabelItemList.clear();
            this.mLabelItemList.addAll(labelInfo.getLabelItemList());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pop_view_dismiss:
                dismiss();
                break;
            case R.id.yqz_reset_button_popwindow_special_performance:
                for (LabelItem labelItem : mLabelItemList) {
                    labelItem.setSelected(false);
                }
                selectedLabelItem=null;
                adapter.notifyDataSetChanged();
                break;
            case R.id.yqz_sure_button_popwindow_special_performance:
                if (listener != null) {
                    listener.onSureClick(labelInfo, selectedLabelItem);
                }
                dismiss();
                break;
        }
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            //屏幕高度减去view的高度得到popupwindow的高度
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    public void setOnSureClickListener(OnSureClickLister listener) {
        this.listener = listener;
    }

    public interface OnSureClickLister {
        public void onSureClick(LabelInfo type, LabelItem item);
    }
}
