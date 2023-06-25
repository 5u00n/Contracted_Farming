package com.ashish.contractedfarming.Farmer.Dashboard.Story;

public class FarmerStoryModel {
    String userUID, username,user_img_url, img_url,story_text,story_time;

    public FarmerStoryModel(String userUID, String username,String user_img_url, String img_url, String story_text,String story_time) {
        this.userUID = userUID;
        this.username = username;
        this.img_url = img_url;
        this.story_text = story_text;
        this.story_time= story_time;
        this.user_img_url=user_img_url;
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



    public String getStory_time() {
        return story_time;
    }

    public void setStory_time(String story_time) {
        this.story_time = story_time;
    }


    public String getUser_img_url() {
        return user_img_url;
    }

    public void setUser_img_url(String user_img_url) {
        this.user_img_url = user_img_url;
    }
}
