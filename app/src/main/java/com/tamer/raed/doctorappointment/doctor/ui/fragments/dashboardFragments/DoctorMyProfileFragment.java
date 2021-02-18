package com.tamer.raed.doctorappointment.doctor.ui.fragments.dashboardFragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.core.app.ActivityCompat;
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

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorMyProfileFragment extends Fragment {
    private CircleImageView imageView;
    private TextView tv_username, tv_number_of_patient, tv_experience, tv_phone,
            tv_specialization, tv_location, tv_working_hour, tv_biography;
    private FirebaseUser firebaseUser;
    private ProgressBar progressBar;
    private Group group;
    private static final int PERMISSION_CODE = 1001;
    private static final int IMAGE_PICK_CODE = 1000;
    private Uri filePath;
    private StorageReference storageReference;

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

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        ImageButton imageButton = view.findViewById(R.id.imgBtn_change_image);
        imageButton.setOnClickListener(view1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    pickImageFromGallery();
                }
                uploadImage();
            } else {
                pickImageFromGallery();
                uploadImage();
            }
        });
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
                            .setDuration(3000)
                            .setBackgroundColorRes(R.color.teal_200)
                            .show();
                }
            } else {
                Alerter.create(getActivity())
                        .setText(getString(R.string.general_error))
                        .setDuration(3000)
                        .setBackgroundColorRes(R.color.teal_200)
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

    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."),
                IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                //permission was granted
                pickImageFromGallery();
                uploadImage();
            } else {
                //permission was denied
                Toast.makeText(getContext(), getString(R.string.permissions_error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_CODE
                && resultCode == Activity.RESULT_OK
                && data != null
                && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getActivity().getApplicationContext().getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
                uploadImage();

            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        group.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();
        if (filePath != null) {
            StorageReference ref = storageReference.child("profileImages/").child(userId);
            ref.putFile(filePath).addOnSuccessListener(taskSnapshot -> Alerter.create(getActivity())
                    .setText(getString(R.string.image_update))
                    .setDuration(3000)
                    .setBackgroundColorRes(R.color.teal_200)
                    .show()).addOnFailureListener(e -> Alerter.create(getActivity())
                    .setText(getString(R.string.image_update_failed))
                    .setDuration(5000)
                    .setBackgroundColorRes(R.color.teal_200)
                    .show());
        }
        fillDataFromFirebase();
    }
}