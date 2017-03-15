package com.example.liuteng.livedemo.model;

import com.example.liuteng.livedemo.base.Urls;
import com.example.liuteng.livedemo.bean.CourseDetailBean;
import com.example.liuteng.livedemo.bean.serverbean.CourseDetailRes;
import com.example.liuteng.livedemo.util.DataFormatUtil;
import com.example.liuteng.livedemo.util.ResponseUtil;
import com.example.liuteng.livedemo.util.XlfLog;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by liuteng on 2017/3/14.
 */

public class CourseInfo {
    private ResponseUtil responseUtil;

    public CourseInfo() {
        responseUtil = new ResponseUtil();
    }

    private CourseDetailRes detailRes;

    public void getCouserDetail(String mid, final ResListener<CourseDetailBean> listener) {
        OkGo.get(Urls.URL_METHOD + "Info?mid=" + mid)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")           // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        // s 即为所需要的结果
                        XlfLog.d(s);
                        try {
                            detailRes = responseUtil.transformDataToEntity(s, new TypeToken<CourseDetailRes>() {
                            }.getType());
                            if (detailRes.getOk() == 1) {
                                CourseDetailBean detailBean = DataFormatUtil.convertCourseDetailResponse(detailRes);
                                listener.onSuccess(detailBean);
                            } else {
                                listener.failed("获取数据失败");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            listener.failed(e.getMessage());
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }
}
