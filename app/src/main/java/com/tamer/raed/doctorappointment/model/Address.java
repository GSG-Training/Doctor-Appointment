package com.tamer.raed.doctorappointment.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {
    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
    private String country;
    private String city;
    private String street;

    public Address(String country, String city, String street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }

    protected Address(Parcel in) {
        country = in.readString();
        city = in.readString();
        street = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(country);
        parcel.writeString(city);
        parcel.writeString(street);
    }
}
