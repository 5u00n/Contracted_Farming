package com.ashish.contractedfarming.Admin.Dashboard.Farmer;

public class AdminFarmerModel {
    String name, id, place;
    String img;

    public AdminFarmerModel(String id, String name, String place, String img) {
        this.name = name;
        this.place = place;
        this.img = img;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
