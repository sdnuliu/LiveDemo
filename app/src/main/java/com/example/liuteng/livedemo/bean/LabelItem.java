package com.example.liuteng.livedemo.bean;

/**
 * Created by 刘腾 on 2017/3/7.
 */

public class LabelItem {
    private boolean isSelected;
    private String labelContent;

    public LabelItem(boolean isSelected, String labelContent) {
        this.isSelected = isSelected;
        this.labelContent = labelContent;
    }

    public String getLabelContent() {

        return labelContent;
    }

    public void setLabelContent(String labelContent) {
        this.labelContent = labelContent;
    }

    public boolean isSelected() {

        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
