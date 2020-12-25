package com.tamer.raed.doctorappointment.model;

public class Doctor {
    private int image;
    private String username;
    private Category category;
    private float rating;

    public Doctor(int image, String username, Category category, float rating) {
        this.image = image;
        this.username = username;
        this.category = category;
        this.rating = rating;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
