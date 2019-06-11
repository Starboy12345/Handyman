package com.example.handyman.models;

public class User {

    private String UserId;
    private String email;
    private String mobileNumber;
    private String fullName;
    private String occupation;
    private String image;
    private String location;
    private String details;

    public User() {
    }

    public User(String userId, String email, String mobileNumber, String fullName, String occupation,String image) {
        this.image = image;
        UserId = userId;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.fullName = fullName;
        this.occupation = occupation;
    }

    public User(String userId, String email, String mobileNumber, String fullName, String occupation, String image, String details) {
        UserId = userId;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.fullName = fullName;
        this.occupation = occupation;
        this.image = image;
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }




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

