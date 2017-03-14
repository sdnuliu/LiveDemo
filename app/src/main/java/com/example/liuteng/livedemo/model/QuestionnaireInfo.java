package com.example.liuteng.livedemo.model;

import com.example.liuteng.livedemo.base.Urls;
import com.example.liuteng.livedemo.bean.PageBean;
import com.example.liuteng.livedemo.bean.serverbean.QAResponse;
import com.example.liuteng.livedemo.util.DataFormatUtil;
import com.example.liuteng.livedemo.util.ResponseUtil;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by liuteng on 2017/3/10.
 */

public class QuestionnaireInfo {
    private ResponseUtil responseUtil;
    private QAResponse qaResponse;

    public QuestionnaireInfo() {
        responseUtil = new ResponseUtil();
    }

    public void getQuestionInfo(String mid, final QuestionnaireInfo.QuesinfoResponse resInterface) {
        OkGo.get(Urls.URL_METHOD + "Question?mid=" + mid)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")           // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        // s 即为所需要的结果
                        try {
                            qaResponse = responseUtil.transformDataToEntity(s, new TypeToken<QAResponse>() {
                            }.getType());
                            if (qaResponse.getOk() == 1) {
                                PageBean pageBean = DataFormatUtil.convertQAResponse(qaResponse);
                                resInterface.onSussess(pageBean);
                            } else {
                                resInterface.failed("获取数据失败");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            resInterface.failed(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        resInterface.failed(e.getMessage());
                    }
                });
    }

    public interface QuesinfoResponse {
        void onSussess(PageBean qaResponse);

        void failed(String message);
    }
}
