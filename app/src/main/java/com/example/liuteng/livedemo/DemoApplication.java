package com.example.liuteng.livedemo;

import android.app.Application;

import com.example.liuteng.livedemo.util.CommonSharePreference;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;

import java.util.logging.Level;

/**
 * Created by liuteng on 2017/3/3.
 */
public class DemoApplication extends Application {
    public static final boolean isDebug = true;
    public static final String APP_NAME = "LiveDemo";

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        CommonSharePreference.init(this);
        initOkGo();
    }

    private void initOkGo() {
        //必须调用初始化
        OkGo.init(this);
        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("OkGo", Level.INFO, true)
                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.NO_CACHE)
                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
