package com.ashish.contractedfarming.Models;


public class ChatUsersModel {

    String name;
    String image;
    String id;
    String status;


    public ChatUsersModel( String id, String name, String image,String status) {
        this.name = name;
        this.image = image;
        this.id = id;
        this.status = status;
    }

    public ChatUsersModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String uid) {
        this.id = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
