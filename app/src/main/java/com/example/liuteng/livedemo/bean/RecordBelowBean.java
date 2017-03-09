package com.example.liuteng.livedemo.bean;

import java.util.List;

/**
 * Created by 刘腾 on 2017/3/10.
 */

public class RecordBelowBean {
    private String title;
    private List<LabelItem> items;
    private List<RecordBean> recordBeens;
    private boolean isCollected;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<LabelItem> getItems() {
        return items;
    }

    public void setItems(List<LabelItem> items) {
        this.items = items;
    }

    public List<RecordBean> getRecordBeens() {
        return recordBeens;
    }

    public void setRecordBeens(List<RecordBean> recordBeens) {
        this.recordBeens = recordBeens;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }
}
