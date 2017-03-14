package com.example.liuteng.livedemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liuteng on 2017/3/14.
 */

public class CourseDetailBean implements Parcelable {
    private String title;
    private long time;
    private String popular;
    private String lecture;
    private String type;
    private String lectureInfo;
    private String content;
    private boolean isSign;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLectureInfo() {
        return lectureInfo;
    }

    public void setLectureInfo(String lectureInfo) {
        this.lectureInfo = lectureInfo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSign() {
        return isSign;
    }

    public void setSign(boolean sign) {
        isSign = sign;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeLong(this.time);
        dest.writeString(this.popular);
        dest.writeString(this.lecture);
        dest.writeString(this.type);
        dest.writeString(this.lectureInfo);
        dest.writeString(this.content);
        dest.writeByte(this.isSign ? (byte) 1 : (byte) 0);
    }

    public CourseDetailBean() {
    }

    protected CourseDetailBean(Parcel in) {
        this.title = in.readString();
        this.time = in.readLong();
        this.popular = in.readString();
        this.lecture = in.readString();
        this.type = in.readString();
        this.lectureInfo = in.readString();
        this.content = in.readString();
        this.isSign = in.readByte() != 0;
    }

    public static final Parcelable.Creator<CourseDetailBean> CREATOR = new Parcelable.Creator<CourseDetailBean>() {
        @Override
        public CourseDetailBean createFromParcel(Parcel source) {
            return new CourseDetailBean(source);
        }

        @Override
        public CourseDetailBean[] newArray(int size) {
            return new CourseDetailBean[size];
        }
    };
}
