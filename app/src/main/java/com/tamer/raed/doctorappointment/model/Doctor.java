package com.tamer.raed.doctorappointment.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Doctor implements Parcelable {
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
    private int image;
    private String username;
    private int id;
    private float rating;
    private String phone;
    private String gender;
    private String specialization;
    private String country;
    private String city;
    private String street;
    private String startDayWork;
    private String endDayWork;
    private String startHourWork;
    private String endHourWork;
    private int numberOfPatient;
    private int experience;
    private String biography;
    private double timeForEachCase;

    public Doctor(int id, int image, String username, String specialization, String phone, String gender, String country, String city, String street, String startDayWork, String endDayWork, String startHourWork, String endHourWork, double timeForEachCase, int experience, String biography) {
        this.id = id;
        this.image = image;
        this.username = username;
        this.specialization = specialization;
        this.phone = phone;
        this.gender = gender;
        this.country = country;
        this.city = city;
        this.street = street;
        this.startDayWork = startDayWork;
        this.endDayWork = endDayWork;
        this.startHourWork = startHourWork;
        this.endHourWork = endHourWork;
        this.timeForEachCase = timeForEachCase;
        this.experience = experience;
        this.biography = biography;
    }

    protected Doctor(Parcel in) {
        id = in.readInt();
        image = in.readInt();
        username = in.readString();
        specialization = in.readString();
        rating = in.readFloat();
        phone = in.readString();
        gender = in.readString();
        country = in.readString();
        city = in.readString();
        street = in.readString();
        startDayWork = in.readString();
        endDayWork = in.readString();
        startHourWork = in.readString();
        endHourWork = in.readString();
        timeForEachCase = in.readDouble();
        numberOfPatient = in.readInt();
        experience = in.readInt();
        biography = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStartDayWork() {
        return startDayWork;
    }

    public void setStartDayWork(String startDayWork) {
        this.startDayWork = startDayWork;
    }

    public String getEndDayWork() {
        return endDayWork;
    }

    public void setEndDayWork(String endDayWork) {
        this.endDayWork = endDayWork;
    }

    public String getStartHourWork() {
        return startHourWork;
    }

    public void setStartHourWork(String startHourWork) {
        this.startHourWork = startHourWork;
    }

    public String getEndHourWork() {
        return endHourWork;
    }

    public void setEndHourWork(String endHourWork) {
        this.endHourWork = endHourWork;
    }

    public double getTimeForEachCase() {
        return timeForEachCase;
    }

    public void setTimeForEachCase(double timeForEachCase) {
        this.timeForEachCase = timeForEachCase;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(image);
        parcel.writeString(username);
        parcel.writeString(specialization);
        parcel.writeFloat(rating);
        parcel.writeString(phone);
        parcel.writeString(gender);
        parcel.writeString(country);
        parcel.writeString(city);
        parcel.writeString(street);
        parcel.writeString(startDayWork);
        parcel.writeString(endDayWork);
        parcel.writeString(startHourWork);
        parcel.writeString(endHourWork);
        parcel.writeDouble(timeForEachCase);
        parcel.writeInt(numberOfPatient);
        parcel.writeInt(experience);
        parcel.writeString(biography);
    }
}
