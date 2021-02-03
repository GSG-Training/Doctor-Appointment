package com.tamer.raed.doctorappointment.model;

public class Patient {
    private int id;
    private String name;
    private String phone;
    private String gender;
    private int image;

    public Patient(int id, String name, String phone, String gender, int image) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
