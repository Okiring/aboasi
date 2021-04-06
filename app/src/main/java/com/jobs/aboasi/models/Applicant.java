package com.jobs.aboasi.models;

import android.content.Intent;

import java.io.Serializable;

public class Applicant implements Serializable {
    public Applicant(String firstName, String lastName, String email, String phoneNumber, Integer experience, Integer budget) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.experience = experience;
        this.budget = budget;
    }

   public Applicant(){

    }

    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    Integer experience;
    Integer budget;


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }



    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }
}
