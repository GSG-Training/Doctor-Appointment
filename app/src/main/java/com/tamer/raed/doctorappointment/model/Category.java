package com.tamer.raed.doctorappointment.model;

public class Category {
    int image;
    String name;
    boolean state;

    public Category(int image, String name, boolean state) {
        this.image = image;
        this.name = name;
        this.state = state;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
