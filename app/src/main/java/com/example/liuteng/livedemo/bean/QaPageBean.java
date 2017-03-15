package com.example.liuteng.livedemo.bean;

import java.util.ArrayList;

/**
 * Created by liuteng on 2017/3/10.
 */

public class QaPageBean {
    //问卷id
    private int pageId;
    //问卷状态
    private String status;
    //问卷主题
    private String title;
    //题目
    private ArrayList<Quesition> quesitions;


    public ArrayList<Quesition> getQuesitions() {
        return quesitions;
    }
    public void setQuesitions(ArrayList<Quesition> quesitions) {
        this.quesitions = quesitions;
    }

    public int getPageId() {
        return pageId;
    }
    public void setPageId(int pageId) {
        this.pageId = pageId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
