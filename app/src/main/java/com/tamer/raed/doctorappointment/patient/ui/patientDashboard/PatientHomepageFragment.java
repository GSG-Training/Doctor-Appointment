package com.tamer.raed.doctorappointment.patient.ui.patientDashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Category;
import com.tamer.raed.doctorappointment.model.Doctor;
import com.tamer.raed.doctorappointment.patient.adapter.CategoryAdapter;
import com.tamer.raed.doctorappointment.patient.adapter.DoctorAdapter;

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
        doctorAdapter = new DoctorAdapter(temp);
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
    }

    private void fillListDoctors() {
//        int image, String username, Category category, float rating
        doctors.add(new Doctor(0, "Tamer", categories.get(0), 4.5f));
        doctors.add(new Doctor(0, "Omar", categories.get(1), 2.2f));
        doctors.add(new Doctor(0, "Hasan", categories.get(0), 4.6f));
        doctors.add(new Doctor(0, "Mohammed", categories.get(1), 3.9f));
        doctors.add(new Doctor(0, "Bassem", categories.get(0), 1f));
    }

    public List<Doctor> getDoctorByCategoryName(Category category) {
        List<Doctor> temp = new ArrayList<>();
        for (int i = 0; i < doctors.size(); i++) {
            if (category.getName().equals(doctors.get(i).getCategory().getName())) {
                temp.add(doctors.get(i));
            }
        }
        return temp;
    }
}