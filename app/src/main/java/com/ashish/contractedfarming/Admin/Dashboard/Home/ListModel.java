package com.ashish.contractedfarming.Admin.Dashboard.Home;

public class ListModel {
    String name="";
    String count="";
    int img;

    public ListModel(String name, String count, int img) {
        this.name = name;
        this.count = count;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
