package com.example.liuteng.livedemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by liuteng on 2017/3/14.
 */

public class CourseDetailBean {
    private String title;
    private PartMeetingBean meetinBean;
    private String content;
    private List<PartContentBean> partList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PartMeetingBean getMeetinBean() {
        return meetinBean;
    }

    public void setMeetinBean(PartMeetingBean meetinBean) {
        this.meetinBean = meetinBean;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<PartContentBean> getPartList() {
        return partList;
    }

    public void setPartList(List<PartContentBean> partList) {
        this.partList = partList;
    }

    public static class PartMeetingBean implements Parcelable {
        private long startTime;
        private long endTime;
        private String popular;
        private String lecture;
        private int type;
        private String lectureInfo;

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getPopular() {
            return popular;
        }

        public void setPopular(String popular) {
            this.popular = popular;
        }

        public String getLecture() {
            return lecture;
        }

        public void setLecture(String lecture) {
            this.lecture = lecture;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getLectureInfo() {
            return lectureInfo;
        }

        public void setLectureInfo(String lectureInfo) {
            this.lectureInfo = lectureInfo;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.startTime);
            dest.writeLong(this.endTime);
            dest.writeString(this.popular);
            dest.writeString(this.lecture);
            dest.writeInt(this.type);
            dest.writeString(this.lectureInfo);
        }

        public PartMeetingBean() {
        }

        protected PartMeetingBean(Parcel in) {
            this.startTime = in.readLong();
            this.endTime = in.readLong();
            this.popular = in.readString();
            this.lecture = in.readString();
            this.type = in.readInt();
            this.lectureInfo = in.readString();
        }

        public static final Parcelable.Creator<PartMeetingBean> CREATOR = new Parcelable.Creator<PartMeetingBean>() {
            @Override
            public PartMeetingBean createFromParcel(Parcel source) {
                return new PartMeetingBean(source);
            }

            @Override
            public PartMeetingBean[] newArray(int size) {
                return new PartMeetingBean[size];
            }
        };
    }

    public static class PartContentBean {
        private String title;
        private long startTime;
        private long endTime;
        private String lecture;
        private List<String> labels;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getLecture() {
            return lecture;
        }

        public void setLecture(String lecture) {
            this.lecture = lecture;
        }

        public List<String> getLabels() {
            return labels;
        }

        public void setLabels(List<String> labels) {
            this.labels = labels;
        }
    }
}
