package com.example.handyman.models;

public class Customer {

    private String UserId;
    private String email;
    private String mobileNumber;
    private String fullName;
    private String occupation;
    private String image;
    private String location;
    private String details;
    private double latitude,longitude;
    private String distanceBetween;
    String response,reason;
    Long date;

    public Customer() {
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getDistanceBetween() {
        return distanceBetween;
    }

    public void setDistanceBetween(String distanceBetween) {
        this.distanceBetween = distanceBetween;
    }





    public Customer(String userId, String email, String mobileNumber,
                    String fullName, String occupation, String image, String details,
                    double latitude, double longitude) {
        UserId = userId;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.fullName = fullName;
        this.occupation = occupation;
        this.image = image;
        this.details = details;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

