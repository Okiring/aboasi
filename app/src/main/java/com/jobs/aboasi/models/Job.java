package com.jobs.aboasi.models;

import java.io.Serializable;

public class Job implements Serializable {
    public Job(Long deadline, String description, String headline, String category, Integer budget, Long posted, String experience,Boolean isOpen,String village) {
        this.deadline = deadline;
        this.description = description;
        this.headline = headline;
        this.category = category;
        this.budget = budget;
        this.posted = posted;
        this.experience = experience;
        this.isOpen = isOpen;
        this.village = village;
    }

   public Job(){}

   public Long deadline;

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Long getPosted() {
        return posted;
    }

    public void setPosted(Long posted) {
        this.posted = posted;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public  String description;
    public String headline;
    public String category;
    public Integer budget;
    public Long posted;
    public String experience;
    public String id;
    String village;
    public Boolean isOpen;

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }
}
