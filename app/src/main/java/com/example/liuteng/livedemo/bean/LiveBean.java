package com.example.liuteng.livedemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liuteng on 2017/3/1.
 */

public class LiveBean implements Parcelable {
    private int type;
    private String title;
    private String pic;
    private long date;
    private String liveId;
    private int limitNumber;//剩余名额
    private MeetingBean liveMeetingBean;
    private boolean isLiving;//正在直播
    private String popularity;//人气

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }


    public boolean isLiving() {
        return isLiving;
    }

    public void setLiving(boolean living) {
        isLiving = living;
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

    public MeetingBean getLiveMeetingBean() {
        return liveMeetingBean;
    }

    public void setLiveMeetingBean(MeetingBean liveMeetingBean) {
        this.liveMeetingBean = liveMeetingBean;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.title);
        dest.writeString(this.pic);
        dest.writeLong(this.date);
        dest.writeString(this.liveId);
        dest.writeInt(this.limitNumber);
        dest.writeParcelable(this.liveMeetingBean, flags);
        dest.writeByte(this.isLiving ? (byte) 1 : (byte) 0);
        dest.writeString(this.popularity);
    }

    public LiveBean() {
    }

    protected LiveBean(Parcel in) {
        this.type = in.readInt();
        this.title = in.readString();
        this.pic = in.readString();
        this.date = in.readLong();
        this.liveId = in.readString();
        this.limitNumber = in.readInt();
        this.liveMeetingBean = in.readParcelable(MeetingBean.class.getClassLoader());
        this.isLiving = in.readByte() != 0;
        this.popularity = in.readString();
    }

    public static final Parcelable.Creator<LiveBean> CREATOR = new Parcelable.Creator<LiveBean>() {
        @Override
        public LiveBean createFromParcel(Parcel source) {
            return new LiveBean(source);
        }

        @Override
        public LiveBean[] newArray(int size) {
            return new LiveBean[size];
        }
    };
}
