package com.ashish.contractedfarming.Models;


public class PlotModel {
    String plot_img_url,plotID, plotName,userUID, area, village, taluka, dist, state, gat_no, sarvay_no,_7_12,_8a,_7_12_url,_8a_url,approval,plant_id,plant_name;

    public PlotModel(String plotID, String plotName,String userUID,String plot_img_url,String area, String village, String taluka, String dist, String state, String gat_no, String sarvay_no, String _7_12, String _8a, String _7_12_url, String _8a_url) {
        this.plotID = plotID;
        this.userUID = userUID;
        this.area = area;
        this.village = village;
        this.taluka = taluka;
        this.dist = dist;
        this.state = state;
        this.gat_no = gat_no;
        this.sarvay_no = sarvay_no;
        this._7_12 = _7_12;
        this._8a = _8a;
        this._7_12_url = _7_12_url;
        this._8a_url = _8a_url;
        this.plot_img_url=plot_img_url;
        this.plotName=plotName;
    }

    public String getPlant_id() {
        return plant_id;
    }

    public void setPlant_id(String plant_id) {
        this.plant_id = plant_id;
    }

    public String getPlant_name() {
        return plant_name;
    }

    public void setPlant_name(String plant_name) {
        this.plant_name = plant_name;
    }

    public PlotModel(String plot_img_url, String plotID, String plotName, String area, String village, String approval) {
        this.plot_img_url = plot_img_url;
        this.plotID = plotID;
        this.plotName = plotName;
        this.area = area;
        this.village = village;
        this.approval = approval;
    }

    public String getPlotName() {
        return plotName;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public void setPlotName(String plotName) {
        this.plotName = plotName;
    }

    public String getPlot_img_url() {
        return plot_img_url;
    }

    public void setPlot_img_url(String plot_img_url) {
        this.plot_img_url = plot_img_url;
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

    public String get_7_12() {
        return _7_12;
    }

    public void set_7_12(String _7_12) {
        this._7_12 = _7_12;
    }

    public String get_8a() {
        return _8a;
    }

    public void set_8a(String _8a) {
        this._8a = _8a;
    }

    public String get_7_12_url() {
        return _7_12_url;
    }

    public void set_7_12_url(String _7_12_url) {
        this._7_12_url = _7_12_url;
    }

    public String get_8a_url() {
        return _8a_url;
    }

    public void set_8a_url(String _8a_url) {
        this._8a_url = _8a_url;
    }


}