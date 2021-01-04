package com.tamer.raed.doctorappointment.patient.ui.patientDashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Address;
import com.tamer.raed.doctorappointment.model.Category;
import com.tamer.raed.doctorappointment.model.Doctor;
import com.tamer.raed.doctorappointment.model.WorkingHour;
import com.tamer.raed.doctorappointment.patient.adapter.CategoryAdapter;
import com.tamer.raed.doctorappointment.patient.adapter.DoctorAdapter;
import com.tamer.raed.doctorappointment.patient.ui.DoctorDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class PatientHomepageFragment extends Fragment {
    private List<Category> categories;
    private List<Doctor> doctors;
    private List<Address> addresses;
    private List<WorkingHour> workingHours;
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
        addresses = new ArrayList<>();
        workingHours = new ArrayList<>();
        fillListAddresses();
        fillListWorkingHours();
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
                });
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

    private void fillListAddresses() {
//        Address(String country, String city, String street)
        addresses.add(new Address("Palestine", "Gaza", "Alnaser"));
        addresses.add(new Address("Palestine", "Gaza", "Alwhda"));
        addresses.add(new Address("Palestine", "Gaza", "Aljlaa"));
        addresses.add(new Address("Palestine", "Gaza", "Omer Almoktar"));
    }

    private void fillListWorkingHours() {
//        WorkingHour(String startDayWork, String endDayWork, String startHourWork, String endHourWork, double timeForEachCase)
        workingHours.add(new WorkingHour("Saturday", "Thursday", "9", "3", 2));
        workingHours.add(new WorkingHour("Sunday", "Thursday", "10", "4", 1.5));
        workingHours.add(new WorkingHour("Saturday", "Thursday", "8", "2", 1));
        workingHours.add(new WorkingHour("Saturday", "Thursday", "9", "4", 1));
    }

    private void fillListDoctors() {
//Doctor(int image, String username, Category category, float rating, String phone, String gender, Address address, WorkingHour workingHour, int experience)
        doctors.add(new Doctor(0, "Tamer", categories.get(0), 4.5f, "0592899024", "Male", addresses.get(0), workingHours.get(1), 14, "weghhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhEOIRH 23ORH O13HRO1  1 13R 1R  12R 12 12RO1HROH1UOR 123R13R1 34123123 12312 31231 2KBNIO N IOH OHU H HUOH HOH HUO HO HO HO HOH IOHQPWJPQJWEJ"));
        doctors.add(new Doctor(0, "Omar", categories.get(1), 2.2f, "0592899024", "Male", addresses.get(1), workingHours.get(0), 4, "weghhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhEOIRH 23ORH O13HRO1  1 13R 1R  12R 12 12RO1HROH1UOR 123R13R1 34123123 12312 31231 2KBNIO N IOH OHU H HUOH HOH HUO HO HO HO HOH IOHQPWJPQJWEJ"));
        doctors.add(new Doctor(0, "Hasan", categories.get(0), 4.6f, "0592899024", "Male", addresses.get(2), workingHours.get(2), 5, "weghhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhEOIRH 23ORH O13HRO1  1 13R 1R  12R 12 12RO1HROH1UOR 123R13R1 34123123 12312 31231 2KBNIO N IOH OHU H HUOH HOH HUO HO HO HO HOH IOHQPWJPQJWEJ"));
        doctors.add(new Doctor(0, "Mohammed", categories.get(1), 3.9f, "0592899024", "Male", addresses.get(0), workingHours.get(2), 10, "weghhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhEOIRH 23ORH O13HRO1  1 13R 1R  12R 12 12RO1HROH1UOR 123R13R1 34123123 12312 31231 2KBNIO N IOH OHU H HUOH HOH HUO HO HO HO HOH IOHQPWJPQJWEJ"));
        doctors.add(new Doctor(0, "Bassem", categories.get(0), 1f, "0592899024", "Male", addresses.get(1), workingHours.get(1), 7, "weghhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhEOIRH 23ORH O13HRO1  1 13R 1R  12R 12 12RO1HROH1UOR 123R13R1 34123123 12312 31231 2KBNIO N IOH OHU H HUOH HOH HUO HO HO HO HOH IOHQPWJPQJWEJ"));
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu_icon, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search_menu_icon, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_search_item) {
            startActivity(new Intent(getContext(), SearchActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}