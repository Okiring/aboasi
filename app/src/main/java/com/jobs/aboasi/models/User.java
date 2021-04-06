package com.jobs.aboasi.models;

import java.io.Serializable;

public class User implements Serializable {

    public User(){

    }

    public User(String firstName, String lastName, String phoneNumber, String email, String userId, boolean isHirer) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userId = userId;
        this.isHirer = isHirer;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean getIsHirer() {
        return isHirer;
    }

    public void setIsHirer(boolean isHirer) {
        this.isHirer = isHirer;
    }

    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String email;
    public String userId;
    public boolean isHirer;
}
