package com.example.liuteng.livedemo.model;

import android.os.SystemClock;

import com.example.liuteng.livedemo.bean.LiveBean;
import com.example.liuteng.livedemo.bean.LiveMeetingBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by liuteng on 2017/3/3.
 */

public class LiveRecordInfo {
    public static String[] pics = {"http://www.instrument.com.cn/webinaradmintest/images/meetFile/137f28e0-ffcb-4a89-9b50-a1dcad37d06c.jpg",
            "http://www.instrument.com.cn/webinaradmintest/images/meetFile/cf8295de-5f6c-4dfd-bee5-40a52029d48d.jpg",
            "http://www.instrument.com.cn/webinaradmintest/images/meetFile/24092c4d-fa8c-4cf5-81ad-bd6696be3851.jpg",
            "http://www.instrument.com.cn/webinaradmintest/images/meetFile/357a3c05-ad90-4500-94ef-58ccd3cfd4d4.jpg",
            "http://www.instrument.com.cn/webinaradmintest/images/meetFile/d27f280b-89e2-4bc1-b539-7100037d10eb.jpg",
            "http://www.instrument.com.cn/webinaradmintest/images/meetFile/697868b2-8646-4c97-8569-cfef48fb031f.jpg",
            "http://www.instrument.com.cn/webinaradmintest/images/meetFile/fdf78042-3b9a-414b-817f-96ce601d18a6.jpg",
            "http://www.instrument.com.cn/webinaradmintest/images/meetFile/98862237-b9c5-4ff8-932e-7d54f91ec674.jpg",
            "http://www.instrument.com.cn/webinaradmintest/images/meetFile/98862237-b9c5-4ff8-932e-7d54f91ec674.jpg",
            "http://www.instrument.com.cn/webinaradmintest/images/meetFile/137f28e0-ffcb-4a89-9b50-a1dcad37d06c.jpg"
    };
    public static String[] titles = {"高分辨轨道阱质谱对果蔬食品中农药多残留高通量快速筛查的解决方案",
            "Agilent MP-AES 测定鞋类及其部件中重金属元素解决方案",
            "赛默飞土壤监测的整体解决方案",
            "超临界流体色谱技术的新进展及其应用",
            "如何简化前处理并提高真菌毒素的回收率"};

    public static String[] lectors = {"甲", "乙", "丙", "丁", "张三", "微生物检测分会"};

    public List<LiveBean> getLiveData() {
        List<LiveBean> liveBeanList = new ArrayList<>();
        LiveBean liveBean;
        for (int i = 0; i < 10; i++) {
            liveBean = new LiveBean();
            liveBean.setLiveId(i+"");
            liveBean.setPic(pics[i]);
            liveBean.setLimitNumber(new Random().nextInt(100));
            liveBean.setDate(System.currentTimeMillis() - 100 * 100 * i);
            liveBean.setTitle(titles[new Random().nextInt(5)]);
            liveBean.setType(1);
            LiveMeetingBean liveMeetingBean =new LiveMeetingBean();
            if (i != 9) {
                liveMeetingBean.setMeetingType(0);
                liveMeetingBean.setLector(lectors[new Random().nextInt(5)]);
            } else {
                liveMeetingBean.setMeetingType(1);
                liveMeetingBean.setLector(lectors[5]);
            }
            if (i == 0) {
                liveBean.setLiving(true);
            }
            liveBean.setLiveMeetingBean(liveMeetingBean);
            liveBeanList.add(liveBean);
        }
        return liveBeanList;
    }

    public boolean checkTel(String s) {
        if ("18518758281".equals(s)) {
            return true;
        }
        return false;
    }
}
