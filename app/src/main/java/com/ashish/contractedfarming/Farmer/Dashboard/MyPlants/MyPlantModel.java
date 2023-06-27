package com.ashish.contractedfarming.Farmer.Dashboard.MyPlants;

public class MyPlantModel  {

    String myPlant_id,farm_id,plant_id,request_time,accept_time,plant_status,planted_time,background_img_url,plant_img_url,farm_name,plant_name;

    int plant_progress;

    public MyPlantModel() {
    }

    public MyPlantModel(String myPlant_id, String plant_status, int plant_progress, String plant_img_url, String farm_name, String plant_name) {
        this.myPlant_id = myPlant_id;
        this.plant_status = plant_status;
        this.plant_progress = plant_progress;
        this.plant_img_url = plant_img_url;
        this.farm_name = farm_name;
        this.plant_name = plant_name;
    }

    public MyPlantModel(String myPlant_id, String farm_id, String plant_id,String plant_status, String request_time) {
        this.myPlant_id = myPlant_id;
        this.farm_id = farm_id;
        this.plant_id = plant_id;
        this.request_time = request_time;
        this.accept_time = accept_time;
        this.plant_status= plant_status;
    }


    public int getPlant_progress() {
        return plant_progress;
    }

    public void setPlant_progress(int plant_progress) {
        this.plant_progress = plant_progress;
    }

    public String getBackground_img_url() {
        return background_img_url;
    }

    public void setBackground_img_url(String background_img_url) {
        this.background_img_url = background_img_url;
    }

    public String getPlant_img_url() {
        return plant_img_url;
    }

    public void setPlant_img_url(String plant_img_url) {
        this.plant_img_url = plant_img_url;
    }

    public String getFarm_name() {
        return farm_name;
    }

    public void setFarm_name(String farm_name) {
        this.farm_name = farm_name;
    }

    public String getPlant_name() {
        return plant_name;
    }

    public void setPlant_name(String plant_name) {
        this.plant_name = plant_name;
    }

    public String getPlanted_time() {
        return planted_time;
    }

    public void setPlanted_time(String planted_time) {
        this.planted_time = planted_time;
    }

    public String getMyPlant_id() {
        return myPlant_id;
    }

    public void setMyPlant_id(String myPlant_id) {
        this.myPlant_id = myPlant_id;
    }

    public String getFarm_id() {
        return farm_id;
    }

    public void setFarm_id(String farm_id) {
        this.farm_id = farm_id;
    }

    public String getPlant_id() {
        return plant_id;
    }

    public void setPlant_id(String plant_id) {
        this.plant_id = plant_id;
    }

    public String getRequest_time() {
        return request_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
    }

    public String getAccept_time() {
        return accept_time;
    }

    public void setAccept_time(String accept_time) {
        this.accept_time = accept_time;
    }

    public String getPlant_status() {
        return plant_status;
    }

    public void setPlant_status(String plant_status) {
        this.plant_status = plant_status;
    }
}
