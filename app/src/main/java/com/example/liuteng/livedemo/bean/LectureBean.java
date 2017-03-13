package com.example.liuteng.livedemo.bean;

/**
 * Created by liuteng on 2017/3/13.
 */

public class LectureBean {
    private String title;
    private String pic;
    private long date;
    private String lectureId;
    private int statue;//状态
    private boolean isLiving;
    private MeetingBean liveMeetingBean;

    public boolean isLiving() {
        return isLiving;
    }

    public void setLiving(boolean living) {
        isLiving = living;
    }

    private MeetingBean meetingBean;

    public MeetingBean getLiveMeetingBean() {
        return liveMeetingBean;
    }

    public void setLiveMeetingBean(MeetingBean liveMeetingBean) {
        this.liveMeetingBean = liveMeetingBean;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }

    public MeetingBean getMeetingBean() {
        return meetingBean;
    }

    public void setMeetingBean(MeetingBean meetingBean) {
        this.meetingBean = meetingBean;
    }
}
