package com.vp.bottomappbar.model;

/**
 * Created by Android Development on 9/3/2016.
 */
public class SelectUser {
    String imagepath;
    String name;
    String phone;
    String email;
    Boolean checkedBox = false;
    public boolean isSection=false;

    public SelectUser(String imagepath, String name, String phone, String email, Boolean checkedBox, boolean isSection) {
        this.imagepath = imagepath;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.checkedBox = checkedBox;
        this.isSection = isSection;
    }

    public SelectUser(String name, Boolean isSection) {
        this.name = name;
        this.isSection = isSection;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getCheckedBox() {
        return checkedBox;
    }

    public void setCheckedBox(Boolean checkedBox) {
        this.checkedBox = checkedBox;
    }

    public boolean isSection() {
        return isSection;
    }

    public void setSection(boolean section) {
        isSection = section;
    }
}
