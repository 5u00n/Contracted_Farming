package com.ashish.contractedfarming.Models;

public class AddressModel {

    String village, circle, taluka, dist, state, country, pin;

    public AddressModel(String village, String circle, String taluka, String dist, String state, String country, String pin) {
        this.village = village;
        this.circle = circle;
        this.taluka = taluka;
        this.dist = dist;
        this.state = state;
        this.country = country;
        this.pin = pin;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
