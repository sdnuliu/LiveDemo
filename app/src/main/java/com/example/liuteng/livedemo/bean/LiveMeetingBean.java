package com.example.liuteng.livedemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 刘腾 on 2017/3/6.
 */

public class LiveMeetingBean implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lector);
        dest.writeInt(this.meetingType);
    }

    public LiveMeetingBean() {
    }

    protected LiveMeetingBean(Parcel in) {
        this.lector = in.readString();
        this.meetingType = in.readInt();
    }

    public static final Parcelable.Creator<LiveMeetingBean> CREATOR = new Parcelable.Creator<LiveMeetingBean>() {
        @Override
        public LiveMeetingBean createFromParcel(Parcel source) {
            return new LiveMeetingBean(source);
        }

        @Override
        public LiveMeetingBean[] newArray(int size) {
            return new LiveMeetingBean[size];
        }
    };
}
