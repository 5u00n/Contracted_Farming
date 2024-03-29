package com.ashish.contractedfarming.Models;

public class FarmerPlantModel {

    String id,plot_id,plant_id,farmer_id,manager_id, farm_name,plant_name,farmer_name,manager_name,plant_img_url,farm_img_url,farmer_img_url,manager_img_url,approval_admin,approval_manager,final_approval,date_added,date_accepted,planted_time;


    int plant_progress;

    public FarmerPlantModel() {
    }

    public String getManager_img_url() {
        return manager_img_url;
    }

    public void setManager_img_url(String manager_img_url) {
        this.manager_img_url = manager_img_url;
    }

    public FarmerPlantModel(String id, String plant_img_url, String farm_img_url, String farm_name, String plant_name) {
        this.id = id;
        this.plant_img_url = plant_img_url;
        this.farm_img_url = farm_img_url;
        this.farm_name = farm_name;
        this.plant_name = plant_name;
    }

    public FarmerPlantModel(String id, String plot_id, String plant_id, String approval_admin, String approval_manager, String final_approval, String date_added, String date_accepted, String planted_time, String plant_img_url, String plant_name, String farm_img_url, String farm_name) {
        this.id = id;
        this.plot_id = plot_id;
        this.plant_id = plant_id;
        this.approval_admin = approval_admin;
        this.approval_manager = approval_manager;
        this.final_approval = final_approval;
        this.date_added = date_added;
        this.date_accepted = date_accepted;
        this.planted_time = planted_time;
        this.plant_img_url = plant_img_url;
        this.farm_img_url = farm_img_url;
        this.farm_name = farm_name;
        this.plant_name = plant_name;
    }

    public FarmerPlantModel(String id, String plot_id, String plant_id, String farmer_id, String manager_id, String farm_name, String plant_name, String farmer_name, String manager_name, String plant_img_url, String farm_img_url, String farmer_img_url, String manager_img_url, String approval_admin, String approval_manager, String final_approval, String date_added, String date_accepted, String planted_time, int plant_progress) {
        this.id = id;
        this.plot_id = plot_id;
        this.plant_id = plant_id;
        this.farmer_id = farmer_id;
        this.manager_id = manager_id;
        this.farm_name = farm_name;
        this.plant_name = plant_name;
        this.farmer_name = farmer_name;
        this.manager_name = manager_name;
        this.plant_img_url = plant_img_url;
        this.farm_img_url = farm_img_url;
        this.farmer_img_url = farmer_img_url;
        this.manager_img_url = manager_img_url;
        this.approval_admin = approval_admin;
        this.approval_manager = approval_manager;
        this.final_approval = final_approval;
        this.date_added = date_added;
        this.date_accepted = date_accepted;
        this.planted_time = planted_time;
        this.plant_progress = plant_progress;
    }

    public String getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(String farmer_id) {
        this.farmer_id = farmer_id;
    }

    public String getFarmer_name() {
        return farmer_name;
    }

    public void setFarmer_name(String farmer_name) {
        this.farmer_name = farmer_name;
    }

    public String getFarmer_img_url() {
        return farmer_img_url;
    }

    public void setFarmer_img_url(String farmer_img_url) {
        this.farmer_img_url = farmer_img_url;
    }

    public String getPlanted_time() {
        return planted_time;
    }

    public void setPlanted_time(String planted_time) {
        this.planted_time = planted_time;
    }

    public String getPlant_img_url() {
        return plant_img_url;
    }

    public void setPlant_img_url(String plant_img_url) {
        this.plant_img_url = plant_img_url;
    }

    public String getFarm_img_url() {
        return farm_img_url;
    }

    public void setFarm_img_url(String farm_img_url) {
        this.farm_img_url = farm_img_url;
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

    public int getPlant_progress() {
        return plant_progress;
    }

    public void setPlant_progress(int plant_progress) {
        this.plant_progress = plant_progress;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlot_id() {
        return plot_id;
    }

    public void setPlot_id(String plot_id) {
        this.plot_id = plot_id;
    }

    public String getPlant_id() {
        return plant_id;
    }

    public void setPlant_id(String plant_id) {
        this.plant_id = plant_id;
    }

    public String getApproval_admin() {
        return approval_admin;
    }

    public void setApproval_admin(String approval_admin) {
        this.approval_admin = approval_admin;
    }

    public String getApproval_manager() {
        return approval_manager;
    }

    public void setApproval_manager(String approval_manager) {
        this.approval_manager = approval_manager;
    }

    public String getFinal_approval() {
        return final_approval;
    }

    public void setFinal_approval(String final_approval) {
        this.final_approval = final_approval;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getDate_accepted() {
        return date_accepted;
    }

    public void setDate_accepted(String date_accepted) {
        this.date_accepted = date_accepted;
    }


    public String getManager_id() {
        return manager_id;
    }

    public void setManager_id(String manager_id) {
        this.manager_id = manager_id;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }


}
