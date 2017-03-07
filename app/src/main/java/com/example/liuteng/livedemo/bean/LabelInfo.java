package com.example.liuteng.livedemo.bean;

import java.util.List;

/**
 * Created by liuteng on 2017/3/7.
 */

public class LabelInfo {
    private String labelType;
    private List<LabelItem> labelItemList;

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    public List<LabelItem> getLabelItemList() {
        return labelItemList;
    }

    public void setLabelItemList(List<LabelItem> labelItemList) {
        this.labelItemList = labelItemList;
    }
}
