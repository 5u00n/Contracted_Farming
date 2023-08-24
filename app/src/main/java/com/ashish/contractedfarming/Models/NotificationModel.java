package com.ashish.contractedfarming.Models;

public class NotificationModel {
    public String not_id,creator_type,creator,message,date_created,type,seen;

    public NotificationModel(String not_id, String creator_type, String creator, String message, String date_created, String type, String seen) {
        this.not_id=not_id;
        this.creator_type=creator_type;
        this.creator = creator;
        this.message = message;
        this.date_created = date_created;
        this.type = type;
        this.seen=seen;
    }

    public String getNot_id() {
        return not_id;
    }

    public void setNot_id(String not_id) {
        this.not_id = not_id;
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

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }
}
