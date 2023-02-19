package com.ashish.contractedfarming.Farmer.Notification;

public class FarmerNotificationModel {
    String name,text;
    int img;

    public FarmerNotificationModel(String name, String text, int img) {
        this.name = name;
        this.text = text;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
