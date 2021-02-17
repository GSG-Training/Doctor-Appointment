package com.tamer.raed.doctorappointment.model;

public class Appointment {
    private String doctorId;
    private String patientId;
    private int day;
    private String month;
    private String year;
    private String time;

    public Appointment(String doctorId, String patientId, int day, String month, String year, String time) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.day = day;
        this.month = month;
        this.year = year;
        this.time = time;
    }

    public Appointment() {
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
