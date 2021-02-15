package com.tamer.raed.doctorappointment.patient.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.tamer.raed.doctorappointment.model.Patient;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientMyProfileFragment extends Fragment {
    private CircleImageView imageView;
    private TextView tv_username, tv_phone, tv_email, tv_gender;
    private ProgressBar progressBar;
    private Group group;
    private FirebaseUser firebaseUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        imageView = view.findViewById(R.id.patient_my_profile_image_view);
        tv_username = view.findViewById(R.id.patient_my_profile_username);
        tv_phone = view.findViewById(R.id.patient_my_profile_phone);
        tv_email = view.findViewById(R.id.patient_my_profile_email);
        tv_gender = view.findViewById(R.id.patient_my_profile_gender);
        progressBar = view.findViewById(R.id.patient_my_profile_progressBar);
        group = view.findViewById(R.id.patient_profile_group);

        fillDataFromFirebase();

        return view;
    }

    private void fillDataFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("Patients").document(firebaseUser.getUid());
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Patient patient = document.toObject(Patient.class);
                    if (patient != null) {
                        tv_username.setText(patient.getName());
                        tv_phone.setText(patient.getPhone());
                        tv_gender.setText(patient.getGender());
                        tv_email.setText(patient.getEmail());
                        getImageProfile();
                        group.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.general_error), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), getString(R.string.general_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getImageProfile() {
        String userId = firebaseUser.getUid();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child("profileImages/" + userId).getDownloadUrl().addOnSuccessListener(uri -> Glide.with(getContext())
                .load(uri)
                .into(imageView)).addOnFailureListener(exception -> Toast.makeText(getContext(), getString(R.string.image_error), Toast.LENGTH_SHORT).show());

    }
}

