package com.ashish.contractedfarming.Models;

public class ConferenceModels {

    String conf_id, conf_topic,conf_date,conf_venue,conf_img_url,created_date;

    public ConferenceModels(String conf_id, String conf_topic, String conf_date, String conf_venue, String creator_img_url, String created_date) {
        this.conf_id = conf_id;
        this.conf_topic = conf_topic;
        this.conf_date = conf_date;
        this.conf_venue = conf_venue;
        this.conf_img_url = creator_img_url;
        this.created_date = created_date;
    }

    public String getConf_id() {
        return conf_id;
    }

    public void setConf_id(String conf_id) {
        this.conf_id = conf_id;
    }

    public String getConf_topic() {
        return conf_topic;
    }

    public void setConf_topic(String conf_topic) {
        this.conf_topic = conf_topic;
    }

    public String getConf_date() {
        return conf_date;
    }

    public void setConf_date(String conf_date) {
        this.conf_date = conf_date;
    }

    public String getConf_venue() {
        return conf_venue;
    }

    public void setConf_venue(String conf_venue) {
        this.conf_venue = conf_venue;
    }

    public String getCreator_img_url() {
        return conf_img_url;
    }

    public void setCreator_img_url(String creator_img_url) {
        this.conf_img_url = creator_img_url;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
}
