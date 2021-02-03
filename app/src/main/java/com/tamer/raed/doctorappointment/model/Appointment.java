package com.tamer.raed.doctorappointment.model;

import java.util.Date;

public class Appointment {
    private int doctorId;
    private int patientId;
    private Date date;

    public Appointment(int doctorId, int patientId, Date date) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
    }

    public Appointment(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
