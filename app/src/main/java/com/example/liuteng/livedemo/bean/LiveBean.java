package com.example.liuteng.livedemo.bean;

/**
 * Created by liuteng on 2017/3/1.
 */

public class LiveBean {
    private int type;
    private String title;
    private String pic;
    private long date;
    private int limitNumber;//剩余名额
    private LiveMeetingBean liveMeetingBean;
    private boolean isLiving;//正在直播

    public boolean isLiving() {
        return isLiving;
    }

    public void setLiving(boolean living) {
        isLiving = living;
    }

    public class LiveMeetingBean {
        private String lector;
        private int meetingType;

        public String getLector() {
            return lector;
        }

        public void setLector(String lector) {
            this.lector = lector;
        }

        public int getMeetingType() {
            return meetingType;
        }

        public void setMeetingType(int meetingType) {
            this.meetingType = meetingType;
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getLimitNumber() {
        return limitNumber;
    }

    public void setLimitNumber(int limitNumber) {
        this.limitNumber = limitNumber;
    }

    public LiveMeetingBean getLiveMeetingBean() {
        return liveMeetingBean;
    }

    public void setLiveMeetingBean(LiveMeetingBean liveMeetingBean) {
        this.liveMeetingBean = liveMeetingBean;
    }
}
