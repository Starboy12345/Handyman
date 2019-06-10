package com.example.handyman;

public class Requestdetailsclass {
    private String UserId;
    private String fullName;
    private String messasge;
    private String occupation;

    public Requestdetailsclass(String userId, String fullName, String messasge, String occupation) {
        UserId = userId;
        this.fullName = fullName;
        this.messasge = messasge;
        this.occupation = occupation;

    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMessasge() {
        return messasge;
    }

    public void setMessasge(String messasge) {
        this.messasge = messasge;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

}
