package com.ashish.contractedfarming.Admin.Dashboard.News;

public class AdminNewsModel {
    String id,topic,date,data,imgurl;

    public AdminNewsModel(String id, String topic, String date, String data, String imgurl) {
        this.id = id;
        this.topic = topic;
        this.date = date;
        this.data = data;
        this.imgurl = imgurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
