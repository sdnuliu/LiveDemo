package com.example.liuteng.livedemo.bean;

/**
 * Created by liuteng on 2017/3/13.
 */

public class EndLectureBean {
    private String previewPic;
    private String title;
    private int status;
    private long publishDate;
    private String recordId;
    private boolean allowRewatch;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(long publishDate) {
        this.publishDate = publishDate;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public boolean isAllowRewatch() {
        return allowRewatch;
    }

    public void setAllowRewatch(boolean allowRewatch) {
        this.allowRewatch = allowRewatch;
    }
}
