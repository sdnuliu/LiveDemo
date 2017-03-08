package com.example.liuteng.livedemo.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.liuteng.livedemo.R;

/**
 * Created by liuteng on 2017/3/8.
 */

public class TitleView extends RelativeLayout {
    public ImageView backIv;
    private Context mContext;
    public TextView title;
    public ImageView shareView;

    public TitleView(Context context) {
        super(context);
        init(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.title_view, this);
        backIv = (ImageView) rootView.findViewById(R.id.iv_back);
        title = (TextView) rootView.findViewById(R.id.tv_common_title);
        shareView = (ImageView) rootView.findViewById(R.id.iv_share);
        backIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).finish();
            }
        });
    }

}
