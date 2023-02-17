package com.ashish.contractedfarming.Models;

public class VerificationModel {

    String aadhaar, aadhaar_url,pan,pan_url,passbook,passbook_url;

    public VerificationModel(String aadhaar, String aadhaar_url, String pan, String pan_url, String passbook, String passbook_url) {
        this.aadhaar = aadhaar;
        this.aadhaar_url = aadhaar_url;
        this.pan = pan;
        this.pan_url = pan_url;
        this.passbook = passbook;
        this.passbook_url = passbook_url;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    public String getAadhaar_url() {
        return aadhaar_url;
    }

    public void setAadhaar_url(String aadhaar_url) {
        this.aadhaar_url = aadhaar_url;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getPan_url() {
        return pan_url;
    }

    public void setPan_url(String pan_url) {
        this.pan_url = pan_url;
    }

    public String getPassbook() {
        return passbook;
    }

    public void setPassbook(String passbook) {
        this.passbook = passbook;
    }

    public String getPassbook_url() {
        return passbook_url;
    }

    public void setPassbook_url(String passbook_url) {
        this.passbook_url = passbook_url;
    }
}
