package com.tamer.raed.doctorappointment.users.doctor.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.activity.LoginActivity;
import com.tamer.raed.doctorappointment.model.Doctor;
import com.tamer.raed.doctorappointment.users.doctor.ui.fragments.dashboardFragments.DoctorConcatUsFragment;
import com.tamer.raed.doctorappointment.users.doctor.ui.fragments.dashboardFragments.DoctorMyProfileFragment;
import com.tamer.raed.doctorappointment.users.doctor.ui.fragments.dashboardFragments.DoctorOrdersFragment;
import com.tamer.raed.doctorappointment.users.doctor.ui.fragments.dashboardFragments.DoctorRecentAppointmentsFragment;
import com.tamer.raed.doctorappointment.users.doctor.ui.fragments.dashboardFragments.DoctorUpcomingAppointmentsFragment;

import java.util.Objects;


public class DoctorDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView imageView;
    private TextView tv_username;
    private FirebaseUser firebaseUser;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        initViews();

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        DoctorUpcomingAppointmentsFragment doctorUpcomingAppointmentsFragment = new DoctorUpcomingAppointmentsFragment();
        moveFragment(doctorUpcomingAppointmentsFragment);
        toolbar.setTitle(getString(R.string.upcoming_appointments));
        fillHeaderData();

    }

    public void initViews() {
        toolbar = findViewById(R.id.doctor_dashboard_toolbar);
        drawerLayout = findViewById(R.id.doctor_drawer_layout);
        navigationView = findViewById(R.id.doctor_navigation_view);
        View header = navigationView.getHeaderView(0);
        imageView = header.findViewById(R.id.header_image_view);
        tv_username = header.findViewById(R.id.tv_header_username);
    }

    private void fillHeaderData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("Doctors").document(firebaseUser.getUid());
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Doctor doctor = document.toObject(Doctor.class);
                    if (doctor != null) {
                        tv_username.setText(doctor.getUsername());
                        getImageProfile();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.general_error), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.general_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getImageProfile() {
        String userId = firebaseUser.getUid();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child("profileImages/" + userId).getDownloadUrl().addOnSuccessListener(uri -> Glide.with(getApplicationContext())
                .load(uri)
                .into(imageView)).addOnFailureListener(exception -> imageView.setImageResource(R.drawable.ic_user_account));

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
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                startActivity(new Intent(DoctorDashboardActivity.this, LoginActivity.class));
                finish();
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
}