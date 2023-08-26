package com.ashish.contractedfarming.Models;

public class RequestModel {

    String id, sender_id,sender_name, send_to, type, type_id, date_of_creation, checked, stared,message;

    public RequestModel(){

    }

    public RequestModel(String id, String sender_id,String sender_name, String send_to, String type, String type_id, String date_of_creation, String checked, String stared,String message) {
        this.id = id;
        this.sender_id = sender_id;
        this.sender_name = sender_name;
        this.send_to = send_to;
        this.type = type;
        this.type_id = type_id;
        this.date_of_creation = date_of_creation;
        this.checked = checked;
        this.stared = stared;
        this.message=message;
    }

    public RequestModel(String id, String sender_id, String send_to, String type, String type_id, String date_of_creation, String checked, String stared,String message) {
        this.id = id;
        this.sender_id = sender_id;
        this.send_to = send_to;
        this.type = type;
        this.type_id = type_id;
        this.date_of_creation = date_of_creation;
        this.checked = checked;
        this.stared = stared;
        this.message=message;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
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
