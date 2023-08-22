package com.ashish.contractedfarming.Admin.Dashboard.Manager;

public class AdminManagerModel {
    String name, place, id,imgurl;

    public AdminManagerModel() {
    }

    public AdminManagerModel(String id, String name, String place, String imgurl) {
        this.name = name;
        this.place = place;
        this.id = id;
        this.imgurl = imgurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
