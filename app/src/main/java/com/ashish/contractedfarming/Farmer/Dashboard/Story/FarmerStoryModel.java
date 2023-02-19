package com.ashish.contractedfarming.Farmer.Dashboard.Story;

public class FarmerStoryModel {
    String username,imgurl,storyimg,userUID;



    public FarmerStoryModel(String username, String imgurl, String storyimg) {
        this.username = username;
        this.imgurl = imgurl;
        this.storyimg = storyimg;
        this.userUID = userUID;
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

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getStoryimg() {
        return storyimg;
    }

    public void setStoryimg(String storyimg) {
        this.storyimg = storyimg;
    }
}
