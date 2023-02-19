package com.ashish.contractedfarming.Models;


public class PlotModel {
    String plotID, userUID, area, village, taluka, dist, state, gat_no, sarvay_no, plant;

    public PlotModel(String plotID, String userUID, String area, String village, String taluka, String dist, String state, String gat_no, String sarvay_no, String plant) {
        this.plotID = plotID;
        this.userUID = userUID;
        this.area = area;
        this.village = village;
        this.taluka = taluka;
        this.dist = dist;
        this.state = state;
        this.gat_no = gat_no;
        this.sarvay_no = sarvay_no;
        this.plant = plant;
    }

    public String getPlotID() {
        return plotID;
    }

    public void setPlotID(String plotID) {
        this.plotID = plotID;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGat_no() {
        return gat_no;
    }

    public void setGat_no(String gat_no) {
        this.gat_no = gat_no;
    }

    public String getSarvay_no() {
        return sarvay_no;
    }

    public void setSarvay_no(String sarvay_no) {
        this.sarvay_no = sarvay_no;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }
}