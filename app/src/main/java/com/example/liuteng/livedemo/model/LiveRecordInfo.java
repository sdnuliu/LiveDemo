package com.example.liuteng.livedemo.model;

import android.os.SystemClock;

import com.example.liuteng.livedemo.bean.LabelInfo;
import com.example.liuteng.livedemo.bean.LabelItem;
import com.example.liuteng.livedemo.bean.LiveBean;
import com.example.liuteng.livedemo.bean.LiveMeetingBean;
import com.example.liuteng.livedemo.bean.RecordBean;

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
    public static String[] recordPics = {"http://img.bokecc.com/comimage/D9180EE599D5BD46/2017-03-06/A0C17A436BE87F719C33DC5901307461-0.jpg",
            "http://img.bokecc.com/comimage/D9180EE599D5BD46/2017-03-06/431EF5E1297381A29C33DC5901307461-0.jpg",
            "http://img.bokecc.com/comimage/D9180EE599D5BD46/2017-03-03/3FD452CE5B737C5D9C33DC5901307461-0.jpg",
            "http://3-img.bokecc.com/comimage/D9180EE599D5BD46/2017-03-03/DC06209A20C188779C33DC5901307461-0.jpg",
            "http://img.bokecc.com/comimage/D9180EE599D5BD46/2017-03-03/337AB45058633EE49C33DC5901307461-0.jpg"};

    public static String[] recordTitles = {"德国retsch莱驰刀式研磨仪GM300产品介绍视频",
            "德国莱驰全自动冷冻混合研磨仪Cryomill 视频介绍",
            "德国retsch莱驰超离心研磨仪ZM200产品介绍视频",
            "德国retsch莱驰RS300XL研磨水泥炉渣做样操作视频",
            "德国莱驰全自动冷冻混合研磨仪Cryomill 视频介绍"};

    public static String[] lectors = {"甲", "乙", "丙", "丁", "张三", "微生物检测分会"};

    public List<LiveBean> getLiveData() {
        List<LiveBean> liveBeanList = new ArrayList<>();
        LiveBean liveBean;
        for (int i = 0; i < 10; i++) {
            liveBean = new LiveBean();
            liveBean.setLiveId(i + "");
            liveBean.setPic(pics[i]);
            liveBean.setPopularity(new Random().nextInt(100) + "");
            liveBean.setDate(System.currentTimeMillis() - 100 * 100 * i);
            liveBean.setTitle(titles[new Random().nextInt(5)]);
            liveBean.setType(1);
            LiveMeetingBean liveMeetingBean = new LiveMeetingBean();
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

    public List<RecordBean> getRecrodData() {
        List<RecordBean> recordBeanList = new ArrayList<>();
        RecordBean recordBean;
        for (int i = 0; i < 10; i++) {
            recordBean = new RecordBean();
            recordBean.setRecordId(i + "");
            recordBean.setPreviewPic(recordPics[new Random().nextInt(5)]);
            recordBean.setPlayTimes(new Random().nextInt(100));
            recordBean.setPublishDate(System.currentTimeMillis() - 100 * 100 * i);
            recordBean.setTitle(recordTitles[new Random().nextInt(5)]);
            recordBeanList.add(recordBean);
        }
        return recordBeanList;
    }

    public List<LabelInfo> getLabelData() {
        List<LabelInfo> labelInfos = new ArrayList<>();
        LabelInfo lablInfo = new LabelInfo();
        lablInfo.setLabelType("仪器");
        List<LabelItem> labelItems = new ArrayList<>();
        labelItems.add(new LabelItem(false,"色谱"));
        labelItems.add(new LabelItem(false,"热分析"));
        labelItems.add(new LabelItem(false,"电镜"));
        labelItems.add(new LabelItem(false,"核磁"));
        labelItems.add(new LabelItem(false,"X射线仪器"));
        labelItems.add(new LabelItem(false,"光谱"));
        labelItems.add(new LabelItem(false,"质谱"));
        lablInfo.setLabelItemList(labelItems);
        labelInfos.add(0,lablInfo);
        lablInfo = new LabelInfo();
        lablInfo.setLabelType("行业");
        labelItems = new ArrayList<>();
        labelItems.add(new LabelItem(false,"制药化妆品"));
        labelItems.add(new LabelItem(false,"食品/饮料"));
        labelItems.add(new LabelItem(false,"环境/水工业"));
        labelItems.add(new LabelItem(false,"医疗/卫生"));
        lablInfo.setLabelItemList(labelItems);
        labelInfos.add(1,lablInfo);
        lablInfo = new LabelInfo();
        lablInfo.setLabelType("专家");
        labelItems = new ArrayList<>();
        labelItems.add(new LabelItem(false,"胡克平"));
        labelItems.add(new LabelItem(false,"杨美华"));
        labelItems.add(new LabelItem(false,"屈风"));
        labelItems.add(new LabelItem(false,"张三"));
        lablInfo.setLabelItemList(labelItems);
        labelInfos.add(2,lablInfo);
        lablInfo = new LabelInfo();
        lablInfo.setLabelType("厂商");
        labelItems = new ArrayList<>();
        labelItems.add(new LabelItem(false,"岛津"));
        labelItems.add(new LabelItem(false,"沃特世"));
        labelItems.add(new LabelItem(false,"徕卡"));
        labelItems.add(new LabelItem(false,"默克化工"));
        labelItems.add(new LabelItem(false,"布鲁克"));
        lablInfo.setLabelItemList(labelItems);
        labelInfos.add(3,lablInfo);
        return labelInfos;
    }
}
