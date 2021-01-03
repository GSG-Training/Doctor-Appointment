package com.tamer.raed.doctorappointment.model;

import android.os.Parcel;
import android.os.Parcelable;

public class WorkingHour implements Parcelable {
    public static final Creator<WorkingHour> CREATOR = new Creator<WorkingHour>() {
        @Override
        public WorkingHour createFromParcel(Parcel in) {
            return new WorkingHour(in);
        }

        @Override
        public WorkingHour[] newArray(int size) {
            return new WorkingHour[size];
        }
    };
    private String startDayWork;
    private String endDayWork;
    private String startHourWork;
    private String endHourWork;
    private double timeForEachCase;

    public WorkingHour(String startDayWork, String endDayWork, String startHourWork, String endHourWork, double timeForEachCase) {
        this.startDayWork = startDayWork;
        this.endDayWork = endDayWork;
        this.startHourWork = startHourWork;
        this.endHourWork = endHourWork;
        this.timeForEachCase = timeForEachCase;
    }

    protected WorkingHour(Parcel in) {
        startDayWork = in.readString();
        endDayWork = in.readString();
        startHourWork = in.readString();
        endHourWork = in.readString();
        timeForEachCase = in.readDouble();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(startDayWork);
        parcel.writeString(endDayWork);
        parcel.writeString(startHourWork);
        parcel.writeString(endHourWork);
        parcel.writeDouble(timeForEachCase);
    }
}
