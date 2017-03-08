package com.example.liuteng.livedemo.bean;

/**
 * Created by 刘腾 on 2017/3/7.
 */

public class LabelItem {
    private boolean isSelected;
    private String labelId;
    private String labelContent;
    private String imageUrl;

    public LabelItem(boolean isSelected, String labelContent, String imageUrl) {
        this.isSelected = isSelected;
        this.labelContent = labelContent;
        this.imageUrl = imageUrl;
    }

    public LabelItem(boolean isSelected, String labelId, String labelContent, String imageUrl) {
        this.isSelected = isSelected;
        this.labelId = labelId;
        this.labelContent = labelContent;
        this.imageUrl = imageUrl;
    }

    public String getLabelId() {

        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getImageUrl() {

        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

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
