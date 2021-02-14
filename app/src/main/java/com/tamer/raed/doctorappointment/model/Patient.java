package com.tamer.raed.doctorappointment.model;

public class Patient {
    private String id;
    private String name;
    private String phone;
    private String gender;
    private String image;
    private String email;

    public Patient(String id, String name, String phone, String gender, String image, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.image = image;
        this.email = email;
    }

    public Patient() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
