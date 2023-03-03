package com.ashish.contractedfarming.Farmer.Dashboard.Story;

public class FarmerStoryModel {
    String userUID, username, img_url,story_text, story_img;

    public FarmerStoryModel(String userUID, String username, String img_url, String story_text, String story_img) {
        this.userUID = userUID;
        this.username = username;
        this.img_url = img_url;
        this.story_text = story_text;
        this.story_img = story_img;
    }


    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getStory_text() {
        return story_text;
    }

    public void setStory_text(String story_text) {
        this.story_text = story_text;
    }

    public String getStory_img() {
        return story_img;
    }

    public void setStory_img(String story_img) {
        this.story_img = story_img;
    }
}
