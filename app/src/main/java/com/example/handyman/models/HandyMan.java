package com.example.handyman.models;

public class HandyMan {
    private float rating;
    private String userId;
    private String name;
    private String reason;
    private double latitude, longitude;
    private String occupation;
    private String response;
    private  String location;
    private String date;
    private  String details,number;
    private  String image;
    private String distanceBetween;
    private  String senderPhoto;
    private String senderName;
    private String handyManName,handyManPhoto;



    public HandyMan() {
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getHandyManName() {
        return handyManName;
    }

    public void setHandyManName(String handyManName) {
        this.handyManName = handyManName;
    }

    public String getHandyManPhoto() {
        return handyManPhoto;
    }

    public void setHandyManPhoto(String handyManPhoto) {
        this.handyManPhoto = handyManPhoto;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhoto() {
        return senderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        this.senderPhoto = senderPhoto;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }





    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDistanceBetween() {
        return distanceBetween;
    }

    public void setDistanceBetween(String distanceBetween) {
        this.distanceBetween = distanceBetween;
    }
}
