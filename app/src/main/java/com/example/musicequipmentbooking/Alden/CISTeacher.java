package com.example.musicequipmentbooking.Alden;

import java.util.ArrayList;

public class CISTeacher{
    private String email;
    private String password;
    private String userID;
    private String userType;

    public CISTeacher(String email, String password, String userID, String userType) {
        this.email = email;
        this.password = password;
        this.userID = userID;
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "CISTeacher{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userID='" + userID + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }

}
