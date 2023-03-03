package com.ashish.contractedfarming.Farmer.Dashboard.MyFarm;

public class FarmerMyfarmModel {
    String plotID,userUID,area,vill,taluka,dist,state,gatno,survayno,plant;

    public FarmerMyfarmModel(String plotID, String userUID, String area, String vill, String taluka, String dist, String state, String gatno, String survayno, String plant) {

        this.plotID = plotID;
        this.userUID = userUID;
        this.area = area;
        this.vill = vill;
        this.taluka = taluka;
        this.dist = dist;
        this.state = state;
        this.gatno = gatno;
        this.survayno = survayno;
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

    public String getVill() {
        return vill;
    }

    public void setVill(String vill) {
        this.vill = vill;
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

    public String getGatno() {
        return gatno;
    }

    public void setGatno(String gatno) {
        this.gatno = gatno;
    }

    public String getSurvayno() {
        return survayno;
    }

    public void setSurvayno(String survayno) {
        this.survayno = survayno;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }
}


