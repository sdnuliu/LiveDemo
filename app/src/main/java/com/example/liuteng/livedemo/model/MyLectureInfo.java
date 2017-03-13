package com.example.liuteng.livedemo.model;

import com.example.liuteng.livedemo.bean.EndLectureBean;
import com.example.liuteng.livedemo.bean.LectureBean;
import com.example.liuteng.livedemo.bean.MeetingBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.liuteng.livedemo.model.LiveRecordInfo.lectors;
import static com.example.liuteng.livedemo.model.LiveRecordInfo.pics;
import static com.example.liuteng.livedemo.model.LiveRecordInfo.titles;

/**
 * Created by liuteng on 2017/3/13.
 */

public class MyLectureInfo {
    public List<LectureBean> getUnStartedLecture() {
        List<LectureBean> lectureBeanList = new ArrayList<>();
        LectureBean lectureBean;
        for (int i = 0; i < 10; i++) {
            lectureBean = new LectureBean();
            lectureBean.setLectureId(i + "");
            lectureBean.setPic(pics[i]);
            lectureBean.setDate(System.currentTimeMillis() - 100 * 100 * i);
            lectureBean.setTitle(titles[new Random().nextInt(5)]);
            lectureBean.setStatue(new Random().nextInt(2));
            MeetingBean liveMeetingBean = new MeetingBean();
            if (i != 9) {
                liveMeetingBean.setMeetingType(0);
                liveMeetingBean.setLector(lectors[new Random().nextInt(5)]);
            } else {
                liveMeetingBean.setMeetingType(1);
                liveMeetingBean.setLector(lectors[5]);
            }
            if (i == 0) {
                lectureBean.setLiving(true);
            }
            lectureBean.setLiveMeetingBean(liveMeetingBean);
            lectureBeanList.add(lectureBean);
        }
        return lectureBeanList;
    }

    public List<EndLectureBean> getEndLecture() {
        List<EndLectureBean> endlectureBeanList = new ArrayList<>();
        EndLectureBean endLectureBean;
        for (int i = 0; i < 10; i++) {
            endLectureBean = new EndLectureBean();
            endLectureBean.setRecordId("" + i);
            endLectureBean.setPreviewPic(pics[i]);
            endLectureBean.setPublishDate(System.currentTimeMillis() - 100 * 100 * i);
            endLectureBean.setTitle(titles[new Random().nextInt(5)]);
            endLectureBean.setStatus(new Random().nextInt(2));
            if (i == 0) {
                endLectureBean.setAllowRewatch(true);
            }
            endlectureBeanList.add(endLectureBean);
        }
        return endlectureBeanList;
    }
}
