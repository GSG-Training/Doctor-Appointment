package com.tamer.raed.doctorappointment.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {
    int image;
    String name;
    boolean state;

    public Category(int image, String name, boolean state) {
        this.image = image;
        this.name = name;
        this.state = state;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    protected Category(Parcel in) {
        image = in.readInt();
        name = in.readString();
        state = in.readByte() != 0;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(image);
        parcel.writeString(name);
        parcel.writeByte((byte) (state ? 1 : 0));
    }
}
