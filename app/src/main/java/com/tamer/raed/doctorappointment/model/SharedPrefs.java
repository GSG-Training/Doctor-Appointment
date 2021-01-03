package com.tamer.raed.doctorappointment.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefs {
    public final static String SPECIALIZATION_KEY = "specialization";
    public final static String EXPERIENCE_KEY = "experience";
    public final static String BIOGRAPHY_KEY = "biography";
    public final static String COUNTRY_KEY = "country";
    public final static String CITY_KEY = "city";
    public final static String STREET_KEY = "street";
    private final static String MY_PREFS_NAME = "My_Prefs";

    public static void setSpecializationData(Context context, String specialization, String biography, String experience) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(SPECIALIZATION_KEY, specialization);
        editor.putString(BIOGRAPHY_KEY, biography);
        editor.putString(EXPERIENCE_KEY, experience);
        editor.apply();
    }

    public static HashMap<String, String> getSpecializationData(Context context) {
        HashMap<String, String> hashMap = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String specialization = sharedPreferences.getString(SPECIALIZATION_KEY, null);
        String biography = sharedPreferences.getString(BIOGRAPHY_KEY, null);
        String experience = sharedPreferences.getString(EXPERIENCE_KEY, null);

        hashMap.put(SPECIALIZATION_KEY, specialization);
        hashMap.put(BIOGRAPHY_KEY, biography);
        hashMap.put(EXPERIENCE_KEY, experience);
        return hashMap;
    }

    public static void setAddressData(Context context, String country, String city, String street) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(COUNTRY_KEY, country);
        editor.putString(CITY_KEY, city);
        editor.putString(STREET_KEY, street);
        editor.apply();
    }

    public static HashMap<String, String> getAddressData(Context context) {
        HashMap<String, String> hashMap = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String country = sharedPreferences.getString(COUNTRY_KEY, null);
        String city = sharedPreferences.getString(CITY_KEY, null);
        String street = sharedPreferences.getString(STREET_KEY, null);

        hashMap.put(COUNTRY_KEY, country);
        hashMap.put(CITY_KEY, city);
        hashMap.put(STREET_KEY, street);
        return hashMap;
    }
}
