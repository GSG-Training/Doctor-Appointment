package com.tamer.raed.doctorappointment.doctor.ui.fragments.dashboardFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Doctor;
import com.tapadoo.alerter.Alerter;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorMyProfileFragment extends Fragment {
    private CircleImageView imageView;
    private TextView tv_username, tv_number_of_patient, tv_experience, tv_phone,
            tv_specialization, tv_location, tv_working_hour, tv_biography;
    private FirebaseUser firebaseUser;
    private ProgressBar progressBar;
    private Group group;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_my_profile, container, false);
        imageView = view.findViewById(R.id.doctor_my_profile_image_view);
        tv_username = view.findViewById(R.id.doctor_my_profile_username);
        tv_number_of_patient = view.findViewById(R.id.doctor_my_profile_tv_number_of_patient);
        tv_experience = view.findViewById(R.id.doctor_my_profile_tv_experience);
        tv_phone = view.findViewById(R.id.doctor_my_profile_tv_phone);
        tv_specialization = view.findViewById(R.id.doctor_my_profile_tv_specialisation);
        tv_location = view.findViewById(R.id.doctor_my_profile_tv_location);
        tv_working_hour = view.findViewById(R.id.doctor_my_profile_tv_working_hour);
        tv_biography = view.findViewById(R.id.doctor_my_profile_tv_biography);
        progressBar = view.findViewById(R.id.doctor_progressBar);
        group = view.findViewById(R.id.doctorMyProfileGroup);
        fillDataFromFirebase();

        return view;
    }

    private void fillDataFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("Doctors").document(firebaseUser.getUid());
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    Doctor doctor = document.toObject(Doctor.class);
                    if (doctor != null) {
                        tv_username.setText(doctor.getUsername());
                        tv_phone.setText(doctor.getPhone());
                        tv_biography.setText(doctor.getBiography());
                        tv_specialization.setText(doctor.getSpecialization());
                        tv_number_of_patient.setText(String.valueOf(doctor.getNumberOfPatient()));
                        tv_experience.setText(String.valueOf(doctor.getExperience()));
                        String location = doctor.getCountry() + " - " + doctor.getCity() + " - " + doctor.getStreet();
                        tv_location.setText(location);
                        String workHour = doctor.getStartDayWork() + " " + getString(R.string.to) + " " + doctor.getEndDayWork()
                                + "\n" + doctor.getStartHourWork() + " " + getString(R.string.to) + " " + doctor.getEndHourWork();
                        tv_working_hour.setText(workHour);
                        getImageProfile();
                        group.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    Alerter.create(getActivity())
                            .setText(getString(R.string.general_error))
                            .setDuration(5000)
                            .setBackgroundColorRes(R.color.purple_700)
                            .show();
                }
            } else {
                Alerter.create(getActivity())
                        .setText(getString(R.string.general_error))
                        .setDuration(5000)
                        .setBackgroundColorRes(R.color.purple_700)
                        .show();
            }
        });
    }

    private void getImageProfile() {
        String userId = firebaseUser.getUid();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child("profileImages/" + userId).getDownloadUrl().addOnSuccessListener(uri -> Glide.with(getContext())
                .load(uri)
                .into(imageView)).addOnFailureListener(exception -> imageView.setImageResource(R.drawable.ic_user_account));

    }
}