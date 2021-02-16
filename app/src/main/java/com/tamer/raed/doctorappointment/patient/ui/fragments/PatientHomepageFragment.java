package com.tamer.raed.doctorappointment.patient.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Category;
import com.tamer.raed.doctorappointment.model.Doctor;
import com.tamer.raed.doctorappointment.patient.adapter.CategoryAdapter;
import com.tamer.raed.doctorappointment.patient.adapter.DoctorAdapter;
import com.tamer.raed.doctorappointment.patient.ui.activities.DoctorDetailsActivity;
import com.tamer.raed.doctorappointment.patient.ui.activities.SearchActivity;

import java.util.ArrayList;
import java.util.List;

public class PatientHomepageFragment extends Fragment {
    private List<Category> categories;
    private List<Doctor> doctors;
    private CategoryAdapter categoryAdapter;
    private DoctorAdapter doctorAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_patient_homepage, container, false);
        RecyclerView doctor_rv = view.findViewById(R.id.patient_homepage_rv_doctors);
        RecyclerView categories_rv = view.findViewById(R.id.patient_homepage_rv_categories);
        categories = new ArrayList<>();
        doctors = new ArrayList<>();

        fillListCategories();

        categoryAdapter = new CategoryAdapter(getContext(), categories, position -> {
            changeItemState(position);
            List<Doctor> temp = getDoctorByCategoryName(categories.get(position));
            doctorAdapter.setDoctors(temp);
            doctorAdapter.notifyDataSetChanged();
            categoryAdapter.notifyDataSetChanged();
        });

        categories_rv.setAdapter(categoryAdapter);
        fillListDoctors();
        int position1 = categoryAdapter.getPosition();
        Category category = categories.get(position1);
        List<Doctor> temp = getDoctorByCategoryName(category);
        doctorAdapter = new DoctorAdapter(temp,
                doctor -> {
                    Intent intent = new Intent(getContext(), DoctorDetailsActivity.class);
                    intent.putExtra("Doctor", doctor);
                    startActivity(intent);
                }, getContext());
        doctor_rv.setAdapter(doctorAdapter);

        return view;
    }


    private void changeItemState(int position) {
        // Change the Category State
        for (int i = 0; i < categories.size(); i++) {
            categories.get(i).setState(false);
        }
        if (categories.get(position) != null) {
            categories.get(position).setState(true);
        }
    }

    private void fillListCategories() {
        // Fill ArrayList
        categories.add(new Category(R.drawable.ic_tooth, getString(R.string.tooth), true));
        categories.add(new Category(R.drawable.ic_heart, getString(R.string.heart), false));
        categories.add(new Category(R.drawable.ic_eye, getString(R.string.eyes), false));
        categories.add(new Category(R.drawable.ic_bone, getString(R.string.bones), false));
        categories.add(new Category(R.drawable.ic_brain, getString(R.string.brain), false));
    }


    private void fillListDoctors() {
//    public Doctor(int id, int image, String username, String specialization, String phone, String gender, String country, String city, String street, String startDayWork, String endDayWork, String startHourWork, String endHourWork, double timeForEachCase, int experience, String biography) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Doctors")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Doctor doctor = document.toObject(Doctor.class);
                                doctors.add(doctor);
                            }
                        } else {
                            Toast.makeText(getContext(), getString(R.string.general_error), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public List<Doctor> getDoctorByCategoryName(Category category) {
        List<Doctor> temp = new ArrayList<>();
        for (int i = 0; i < doctors.size(); i++) {
            if (category.getName().equalsIgnoreCase(doctors.get(i).getSpecialization())) {
                temp.add(doctors.get(i));
            }
        }
        return temp;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu_icon, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_search_item) {
            startActivity(new Intent(getContext(), SearchActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}