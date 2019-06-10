package com.example.handyman.models;

public class User {
    public User(String userId, String email, String mobileNumber, String fullName, String occupation) {
        UserId = userId;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.fullName = fullName;
        this.occupation = occupation;
    }

    public User(String userId, String email, String mobileNumber, String fullName, String occupation, String about) {
        UserId = userId;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.fullName = fullName;
        this.occupation = occupation;
        this.about = about;
    }

    private String UserId;
    private String email;
    private String mobileNumber;
    private String fullName;
    private String occupation;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    private String about;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}

