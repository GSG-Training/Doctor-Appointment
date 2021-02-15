package com.tamer.raed.doctorappointment.doctor.ui.signUpFragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.doctor.ui.DoctorSignUpActivity;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalDataFragment extends Fragment {

    private static final int PERMISSION_CODE = 1001;
    private static final int IMAGE_PICK_CODE = 1000;
    private String username, phone, password, gender, email;
    private TextInputEditText et_username, et_phone, et_password, et_email;
    private RadioButton rb_male, rb_female;
    private Button next;
    private CircleImageView imageView;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri filePath;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_data, container, true);
        et_username = view.findViewById(R.id.login_data_username);
        et_email = view.findViewById(R.id.login_data_email);
        et_phone = view.findViewById(R.id.login_data_phone);
        et_password = view.findViewById(R.id.login_data_password);
        imageView = view.findViewById(R.id.doctor_image_profile);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ddddd", "clickaergksfc wrsfkdbmc efhksdjc efdhkj.zxm,");
            }
        });
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                    if (getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
////                            == PackageManager.PERMISSION_DENIED) {
////                        //permission not granted, request it.
////                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
////                        //show popup for runtime permission
////                        requestPermissions(permissions, PERMISSION_CODE);
////                    } else {
////                        //permission already granted
////                        pickImageFromGallery();
////                    }
////                } else {
////                    //system os is less then marshmallow
////                    pickImageFromGallery();
////                }
//            }
//        });
        rb_male = view.findViewById(R.id.rb_male);
        rb_female = view.findViewById(R.id.rb_female);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            next = getActivity().findViewById(R.id.sign_up_btn_next);
            if (next != null) {
                next.setOnClickListener(view -> {
                    if (!TextUtils.isEmpty(et_username.getText().toString())) {
                        username = et_username.getText().toString();
                        if (!TextUtils.isEmpty(et_email.getText().toString())) {
                            email = et_email.getText().toString();
                            if (!TextUtils.isEmpty(et_phone.getText().toString())) {
                                phone = et_phone.getText().toString();
                                if (!TextUtils.isEmpty(et_password.getText().toString())) {
                                    password = et_password.getText().toString();
                                    getGender();
                                    TextView text_view_specialization = getActivity().findViewById(R.id.sign_up_text_view_specialization);
                                    View view1 = getActivity().findViewById(R.id.sign_up_view1);
                                    TextView tv_specialization = getActivity().findViewById(R.id.sign_up_tv_specialization);
                                    SpecializationFragment specializationFragment = new SpecializationFragment();

                                    ((DoctorSignUpActivity) getActivity()).doneStep(text_view_specialization, view1, tv_specialization, specializationFragment);
                                } else {
                                    et_password.setError(getString(R.string.password_error));
                                }
                            } else {
                                et_phone.setError(getString(R.string.phone_error));
                            }
                        } else {
                            et_email.setError(getString(R.string.email_error));
                        }
                    } else {
                        et_username.setError(getString(R.string.username_error));
                    }
                });
            }
        }
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
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission was granted
                    pickImageFromGallery();
                } else {
                    //permission was denied
                    Toast.makeText(getContext(), getString(R.string.permissions_error), Toast.LENGTH_SHORT).show();
                }
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

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContext().getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    private void getGender() {
        if (rb_male.isChecked()) {
            gender = getString(R.string.male);
        } else if (rb_female.isChecked()) {
            gender = getString(R.string.female);
        }
    }
}