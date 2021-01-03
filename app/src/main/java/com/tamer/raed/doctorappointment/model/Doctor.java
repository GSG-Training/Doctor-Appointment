package com.tamer.raed.doctorappointment.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Doctor implements Parcelable {
    private int image;
    private String username;
    private Category category;
    private float rating;
    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };
    private String phone;
    private String gender;
    private Address address;
    private WorkingHour workingHour;
    private int numberOfPatient;
    private int experience;
    private String biography;

    public Doctor(int image, String username, Category category, float rating, String phone, String gender, Address address, WorkingHour workingHour, int experience, String biography) {
        this.image = image;
        this.username = username;
        this.category = category;
        this.rating = rating;
        this.phone = phone;
        this.gender = gender;
        this.address = address;
        this.workingHour = workingHour;
        this.experience = experience;
        this.biography = biography;
    }

    protected Doctor(Parcel in) {
        image = in.readInt();
        username = in.readString();
        rating = in.readFloat();
        phone = in.readString();
        gender = in.readString();
        address = in.readParcelable(Address.class.getClassLoader());
        category = in.readParcelable(Category.class.getClassLoader());
        workingHour = in.readParcelable(WorkingHour.class.getClassLoader());
        numberOfPatient = in.readInt();
        experience = in.readInt();
        biography = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(image);
        dest.writeString(username);
        dest.writeFloat(rating);
        dest.writeString(phone);
        dest.writeString(gender);
        dest.writeParcelable(address, flags);
        dest.writeParcelable(category, flags);
        dest.writeParcelable(workingHour, flags);
        dest.writeInt(numberOfPatient);
        dest.writeInt(experience);
        dest.writeString(biography);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public WorkingHour getWorkingHour() {
        return workingHour;
    }

    public void setWorkingHour(WorkingHour workingHour) {
        this.workingHour = workingHour;
    }

    public int getNumberOfPatient() {
        return numberOfPatient;
    }

    public void setNumberOfPatient(int numberOfPatient) {
        this.numberOfPatient = numberOfPatient;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }


}
