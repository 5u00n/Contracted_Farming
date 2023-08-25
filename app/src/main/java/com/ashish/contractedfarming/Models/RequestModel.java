package com.ashish.contractedfarming.Models;

public class RequestModel {

    String id, sender,sender_name, send_to, type, type_id, date_of_creation, checked, stared;

    public RequestModel(){

    }

    public RequestModel(String id, String sender,String sender_name, String send_to, String type, String type_id, String date_of_creation, String checked, String stared) {
        this.id = id;
        this.sender = sender;
        this.sender_name = sender_name;
        this.send_to = send_to;
        this.type = type;
        this.type_id = type_id;
        this.date_of_creation = date_of_creation;
        this.checked = checked;
        this.stared = stared;
    }

    public RequestModel(String id, String sender, String send_to, String type, String type_id, String date_of_creation, String checked, String stared) {
        this.id = id;
        this.sender = sender;
        this.send_to = send_to;
        this.type = type;
        this.type_id = type_id;
        this.date_of_creation = date_of_creation;
        this.checked = checked;
        this.stared = stared;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender_id() {
        return sender;
    }

    public void setSender_id(String sender) {
        this.sender = sender;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSend_to() {
        return send_to;
    }

    public void setSend_to(String send_to) {
        this.send_to = send_to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getDate_of_creation() {
        return date_of_creation;
    }

    public void setDate_of_creation(String date_of_creation) {
        this.date_of_creation = date_of_creation;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getStared() {
        return stared;
    }

    public void setStared(String stared) {
        this.stared = stared;
    }
}
