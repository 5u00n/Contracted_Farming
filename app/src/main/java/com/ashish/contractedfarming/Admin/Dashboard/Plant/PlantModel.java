package com.ashish.contractedfarming.Admin.Dashboard.Plant;

public class PlantModel {
    String id, name, disc, life_span, humidity, fertilizer, water_time, light,temprature, imgurl;

    public PlantModel(String id, String name, String disc, String life_span, String humidity, String fertilizer, String water_time, String light, String temprature, String imgurl) {
        this.id = id;
        this.name = name;
        this.disc = disc;
        this.life_span = life_span;
        this.humidity = humidity;
        this.fertilizer = fertilizer;
        this.water_time = water_time;
        this.light = light;
        this.temprature = temprature;
        this.imgurl = imgurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public String getLife_span() {
        return life_span;
    }

    public void setLife_span(String life_span) {
        this.life_span = life_span;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(String fertilizer) {
        this.fertilizer = fertilizer;
    }

    public String getWater_time() {
        return water_time;
    }

    public void setWater_time(String water_time) {
        this.water_time = water_time;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getTemprature() {
        return temprature;
    }

    public void setTemprature(String temprature) {
        this.temprature = temprature;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
