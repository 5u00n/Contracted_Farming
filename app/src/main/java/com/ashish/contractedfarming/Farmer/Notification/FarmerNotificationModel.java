package com.ashish.contractedfarming.Farmer.Notification;

public class FarmerNotificationModel {
    String creator_type,creator,message,date_created,type;

    public FarmerNotificationModel(String creator_type,String creator, String message, String date_created, String type) {
        this.creator_type=creator_type;
        this.creator = creator;
        this.message = message;
        this.date_created = date_created;
        this.type = type;
    }

    public String getCreator_type() {
        return creator_type;
    }

    public void setCreator_type(String creator_type) {
        this.creator_type = creator_type;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
