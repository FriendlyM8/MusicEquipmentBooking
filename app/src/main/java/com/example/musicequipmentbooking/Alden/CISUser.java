package com.example.musicequipmentbooking.Alden;

import java.util.ArrayList;

public class CISUser {
    private String email;
    private String password;
    private String userID;
    private String userType;
    private int creditScore;
    ArrayList instrumentsBorrowed = new ArrayList();

    //No argument for firestore
    public CISUser(){
    }

    public CISUser(String email, String password, String userID, String userType, int creditScore, ArrayList instrumentsBorrowed) {
        this.email = email;
        this.password = password;
        this.userID = userID;
        this.userType = userType;
        this.creditScore = creditScore;
        this.instrumentsBorrowed = instrumentsBorrowed;
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

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public ArrayList getInstrumentsBorrowed() {
        return instrumentsBorrowed;
    }

    public void setInstrumentsBorrowed(ArrayList instrumentsBorrowed) {
        this.instrumentsBorrowed = instrumentsBorrowed;
    }

    @Override
    public String toString() {
        return "CISUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userID='" + userID + '\'' +
                ", userType='" + userType + '\'' +
                ", creditScore=" + creditScore +
                ", instrumentsBorrowed=" + instrumentsBorrowed +
                '}';
    }
}
