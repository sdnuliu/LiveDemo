package com.example.liuteng.livedemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 刘腾 on 2017/3/6.
 */

public class MeetingBean implements Parcelable {
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

    public MeetingBean() {
    }

    protected MeetingBean(Parcel in) {
        this.lector = in.readString();
        this.meetingType = in.readInt();
    }

    public static final Parcelable.Creator<MeetingBean> CREATOR = new Parcelable.Creator<MeetingBean>() {
        @Override
        public MeetingBean createFromParcel(Parcel source) {
            return new MeetingBean(source);
        }

        @Override
        public MeetingBean[] newArray(int size) {
            return new MeetingBean[size];
        }
    };
}
