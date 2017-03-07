package com.example.liuteng.livedemo.bean;

import java.util.List;

/**
 * Created by liuteng on 2017/3/7.
 */

public class LabelInfo {
    private String labelType;
    private List<String> labelItemList;

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    public List<String> getLabelItemList() {
        return labelItemList;
    }

    public void setLabelItemList(List<String> labelItemList) {
        this.labelItemList = labelItemList;
    }
}
