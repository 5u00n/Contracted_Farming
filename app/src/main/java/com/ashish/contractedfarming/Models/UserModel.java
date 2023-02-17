package com.ashish.contractedfarming.Models;

public class UserModel {
    public String userUID,username,mob_no,img_url,usertype,online_status,approved_num;

    public UserModel(String userUID, String username, String mob_no, String img_url, String approved_num) {
        this.userUID = userUID;
        this.username = username;
        this.mob_no = mob_no;
        this.img_url = img_url;
        this.approved_num = approved_num;
    }

    public UserModel(String userUID, String username, String mob_no, String img_url, String usertype, String online_status, String approved_num) {
        this.userUID = userUID;
        this.username = username;
        this.mob_no = mob_no;
        this.img_url = img_url;
        this.usertype = usertype;
        this.online_status = online_status;
        this.approved_num = approved_num;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getApproved_num() {
        return approved_num;
    }

    public void setApproved_num(String approved_num) {
        this.approved_num = approved_num;
    }

    public String getOnline_status() {
        return online_status;
    }

    public void setOnline_status(String online_status) {
        this.online_status = online_status;
    }

    public String getMob_no() {
        return mob_no;
    }

    public void setMob_no(String mob_no) {
        this.mob_no = mob_no;
    }
}
