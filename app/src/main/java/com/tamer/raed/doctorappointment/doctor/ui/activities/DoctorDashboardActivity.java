package com.tamer.raed.doctorappointment.doctor.ui.activities;

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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import com.google.android.material.navigation.NavigationView;
import com.tamer.raed.doctorappointment.LoginActivity;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.doctor.ui.fragments.dashboardFragments.DoctorConcatUsFragment;
import com.tamer.raed.doctorappointment.doctor.ui.fragments.dashboardFragments.DoctorMyProfileFragment;
import com.tamer.raed.doctorappointment.doctor.ui.fragments.dashboardFragments.DoctorOrdersFragment;
import com.tamer.raed.doctorappointment.doctor.ui.fragments.dashboardFragments.DoctorRecentAppointmentsFragment;
import com.tamer.raed.doctorappointment.doctor.ui.fragments.dashboardFragments.DoctorUpcomingAppointmentsFragment;
import com.tamer.raed.doctorappointment.patient.ui.activities.SettingsActivity;

import java.util.Objects;


public class DoctorDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final int REQUEST_SETTINGS_CHANGE = 1;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        initViews();

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        DoctorUpcomingAppointmentsFragment doctorUpcomingAppointmentsFragment = new DoctorUpcomingAppointmentsFragment();
        moveFragment(doctorUpcomingAppointmentsFragment);
        toolbar.setTitle(getString(R.string.upcoming_appointments));
        readUserPreferences();
    }

    public void initViews() {
        toolbar = findViewById(R.id.doctor_dashboard_toolbar);
        drawerLayout = findViewById(R.id.doctor_drawer_layout);
        navigationView = findViewById(R.id.doctor_navigation_view);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.doctor_menu_upcoming_appointments:
                DoctorUpcomingAppointmentsFragment doctorUpcomingAppointmentsFragment = new DoctorUpcomingAppointmentsFragment();
                moveFragment(doctorUpcomingAppointmentsFragment);
                toolbar.setTitle(getString(R.string.upcoming_appointments));
                toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                drawerLayout.closeDrawers();
                break;
            case R.id.doctor_menu_recent_appointments:
                DoctorRecentAppointmentsFragment doctorRecentAppointmentsFragment = new DoctorRecentAppointmentsFragment();
                moveFragment(doctorRecentAppointmentsFragment);
                toolbar.setTitle(getString(R.string.recent_appointments));
                toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                drawerLayout.closeDrawers();
                break;
            case R.id.doctor_menu_orders:
                DoctorOrdersFragment doctorOrdersFragment = new DoctorOrdersFragment();
                moveFragment(doctorOrdersFragment);
                toolbar.setTitle(getString(R.string.orders));
                toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                drawerLayout.closeDrawers();
                break;
            case R.id.doctor_menu_my_profile:
                DoctorMyProfileFragment doctorMyProfileFragment = new DoctorMyProfileFragment();
                moveFragment(doctorMyProfileFragment);
                toolbar.setTitle(getString(R.string.my_profile));
                toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                drawerLayout.closeDrawers();
                break;
            case R.id.doctor_menu_concat_us:
                DoctorConcatUsFragment doctorConcatUsFragment = new DoctorConcatUsFragment();
                moveFragment(doctorConcatUsFragment);
                toolbar.setTitle(getString(R.string.concat_us));
                toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                drawerLayout.closeDrawers();
                break;
            case R.id.doctor_menu_logout:
                startActivity(new Intent(DoctorDashboardActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.doctor_menu_settings:
                startActivityForResult(new Intent(DoctorDashboardActivity.this, SettingsActivity.class), REQUEST_SETTINGS_CHANGE);
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    public void moveFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out).replace(R.id.doctor_frameLayout, fragment).commit();
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