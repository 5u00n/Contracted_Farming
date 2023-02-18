package com.ashish.contractedfarming.Admin.Dashboard.Plant;

public class AdminPlantsModel {
    String id;
    String plant;
    String img;

    public AdminPlantsModel(String id, String plant, String img) {
        this.id = id;
        this.plant = plant;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}