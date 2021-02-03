package com.tamer.raed.doctorappointment.patient.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import com.google.android.material.navigation.NavigationView;
import com.tamer.raed.doctorappointment.LoginActivity;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.patient.ui.fragments.ConcatUsFragment;
import com.tamer.raed.doctorappointment.patient.ui.fragments.PatientHomepageFragment;
import com.tamer.raed.doctorappointment.patient.ui.fragments.PatientMyProfileFragment;
import com.tamer.raed.doctorappointment.patient.ui.fragments.PatientRecentAppointmentsFragment;
import com.tamer.raed.doctorappointment.patient.ui.fragments.PatientUpcomingAppointmentsFragment;

import java.util.Objects;

public class PatientDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final int REQUEST_SETTINGS_CHANGE = 1;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);

        initViews();

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        PatientHomepageFragment patientHomepageFragment = new PatientHomepageFragment();
        moveFragment(patientHomepageFragment);
        toolbar.setTitle(getString(R.string.homepage));
        readUserPreferences();

    }

    public void initViews() {
        toolbar = findViewById(R.id.patient_dashboard_toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.patient_menu_homepage:
                PatientHomepageFragment patientHomepageFragment = new PatientHomepageFragment();
                moveFragment(patientHomepageFragment);
                toolbar.setTitle(getString(R.string.homepage));
                toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                drawerLayout.closeDrawers();
                break;
            case R.id.patient_menu_my_profile:
                PatientMyProfileFragment patientMyProfileFragment = new PatientMyProfileFragment();
                moveFragment(patientMyProfileFragment);
                toolbar.setTitle(getString(R.string.my_profile));
                toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                drawerLayout.closeDrawers();
                break;
            case R.id.patient_menu_upcoming_appointments:
                PatientUpcomingAppointmentsFragment patientUpcomingAppointmentsFragment = new PatientUpcomingAppointmentsFragment();
                moveFragment(patientUpcomingAppointmentsFragment);
                toolbar.setTitle(getString(R.string.upcoming_appointments));
                toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                drawerLayout.closeDrawers();
                break;
            case R.id.patient_menu_recent_appointments:
                PatientRecentAppointmentsFragment patientRecentAppointmentsFragment = new PatientRecentAppointmentsFragment();
                moveFragment(patientRecentAppointmentsFragment);
                toolbar.setTitle(getString(R.string.recent_appointments));
                toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                drawerLayout.closeDrawers();
                break;
            case R.id.patient_menu_concat_us:
                ConcatUsFragment concatUsFragment = new ConcatUsFragment();
                moveFragment(concatUsFragment);
                toolbar.setTitle(getString(R.string.concat_us));
                toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                drawerLayout.closeDrawers();
                break;
            case R.id.patient_menu_logout:
                startActivityForResult(new Intent(PatientDashboardActivity.this, LoginActivity.class), REQUEST_SETTINGS_CHANGE);
                finish();
                break;
            case R.id.patient_menu_settings:
                startActivity(new Intent(PatientDashboardActivity.this, SettingsActivity.class));
//                SettingsFragment settingsFragment = new SettingsFragment();
//                moveFragment(settingsFragment);
//                toolbar.setTitle(getString(R.string.settings));
//                toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                drawerLayout.closeDrawers();
                break;

        }
        return false;
    }

    public void moveFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out).replace(R.id.patient_frameLayout, fragment).commit();
    }

    private void readUserPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isNight = sharedPreferences.getBoolean(getString(R.string.key_night_mode), getResources().getBoolean(R.bool.night_mode_default));
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SETTINGS_CHANGE) {
            if (resultCode == RESULT_OK) {
                readUserPreferences();
            }
        }
    }
}