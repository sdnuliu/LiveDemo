package com.example.liuteng.livedemo.bean;

/**
 * Created by liuteng on 2017/2/28.
 */

public class RecordBean {
    private String previewPic;
    private String title;
    private int playTimes;
    private long publishDate;
    private String recordId;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getPreviewPic() {
        return previewPic;
    }

    public void setPreviewPic(String previewPic) {
        this.previewPic = previewPic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(int playTimes) {
        this.playTimes = playTimes;
    }

    public long getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(long publishDate) {
        this.publishDate = publishDate;
    }
}
