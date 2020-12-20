package com.tamer.raed.doctorappointment.patient.ui.patientDashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Category;
import com.tamer.raed.doctorappointment.patient.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;


public class PatientHomepageFragment extends Fragment {
    List<Category> categories;
    private RecyclerView doctor_rv, categories_rv;
    private CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_homepage, container, false);
        doctor_rv = view.findViewById(R.id.patient_homepage_rv_doctors);
        categories_rv = view.findViewById(R.id.patient_homepage_rv_categories);
        categories = new ArrayList<>();
        fillListImages();
        categoryAdapter = new CategoryAdapter(categories, new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getContext(), "Position : " + position, Toast.LENGTH_SHORT).show();
            }
        });
        categories_rv.setAdapter(categoryAdapter);
        return view;
    }


    private void fillListImages() {
        categories.add(new Category(R.drawable.ic_tooth, getString(R.string.tooth), true));
        categories.add(new Category(R.drawable.ic_heart, getString(R.string.heart), false));
    }
}