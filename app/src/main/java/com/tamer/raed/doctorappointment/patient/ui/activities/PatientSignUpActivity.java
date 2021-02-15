package com.tamer.raed.doctorappointment.patient.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Patient;

import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientSignUpActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String username, email, phone, password, gender;
    private TextInputEditText et_username, et_phone, et_password, et_email;
    private static final int IMAGE_PICK_CODE = 1000;
    private RadioButton rb_male, rb_female;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private Button signUpBtn;
    private static final int PERMISSION_CODE = 1001;
    private CircleImageView imageView;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri filePath;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_up);
        initViews();
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow));
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        //permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        //permission already granted
                        pickImageFromGallery();
                    }
                } else {
                    //system os is less then marshmallow
                    pickImageFromGallery();
                }
            }
        });
    }

    private void initViews() {
        toolbar = findViewById(R.id.patient_sign_up_toolbar);
        et_username = findViewById(R.id.patient_sign_up_et_username);
        et_phone = findViewById(R.id.patient_sign_up_et_phone);
        et_password = findViewById(R.id.patient_sign_up_et_password);
        et_email = findViewById(R.id.patient_sign_up_et_email);
        rb_male = findViewById(R.id.patient_rb_male);
        rb_female = findViewById(R.id.patient_rb_female);
        progressBar = findViewById(R.id.sign_up_progressBar);
        signUpBtn = findViewById(R.id.patient_sign_up_btn);
        imageView = findViewById(R.id.patient_sign_up_image);
    }

    public void signUp(View view) {
        if (!TextUtils.isEmpty(et_username.getText().toString())) {
            username = et_username.getText().toString();
            if (!TextUtils.isEmpty(et_email.getText().toString())) {
                email = et_email.getText().toString();
                if (!TextUtils.isEmpty(et_phone.getText().toString())) {
                    phone = et_phone.getText().toString();
                    if (!TextUtils.isEmpty(et_password.getText().toString())) {
                        password = et_password.getText().toString();
                        getGender();
                        signUpFirebase();

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
    }

    private void signUpFirebase() {
        signUpBtn.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id = mAuth.getCurrentUser().getUid();
                    Patient patient = new Patient(id, username, phone, gender, email);
                    db = FirebaseFirestore.getInstance();
                    db.collection("Patients").document(mAuth.getCurrentUser().getUid().toString()).set(patient).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PatientSignUpActivity.this, getString(R.string.success_sign_up), Toast.LENGTH_SHORT).show();
                                userId = mAuth.getCurrentUser().getUid();
                                if (userId != null) {
                                    uploadImage();
                                    progressBar.setVisibility(View.GONE);
                                    signUpBtn.setVisibility(View.VISIBLE);
                                    startActivity(new Intent(PatientSignUpActivity.this, PatientDashboardActivity.class));
                                    finish();
                                }

                                progressBar.setVisibility(View.GONE);
                                signUpBtn.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(PatientSignUpActivity.this, getString(R.string.error_sign_up), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    progressBar.setVisibility(View.GONE);
                    signUpBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(PatientSignUpActivity.this, getString(R.string.error_sign_up), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getGender() {
        if (rb_male.isChecked()) {
            gender = getString(R.string.male);
        } else if (rb_female.isChecked()) {
            gender = getString(R.string.female);
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
                    Toast.makeText(this, getString(R.string.permissions_error), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_CODE
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
//        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE && data != null) {
//            //set image to image view
//            imageView.setImageURI(data.getData());
//            bitmap = (Bitmap) data.getExtras().get("data");
////            imageView.setImageBitmap(bitmap);
//        }
    }

    private void uploadImage() {
        if (filePath != null) {
            // Defining the child of storageReference
            StorageReference ref = storageReference.child("profileImages/").child(userId);
            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Image uploaded successfully
                    // Dismiss dialog
                    Toast.makeText(PatientSignUpActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    Toast.makeText(PatientSignUpActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
