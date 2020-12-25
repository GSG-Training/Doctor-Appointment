package com.tamer.raed.doctorappointment.patient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Doctor;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {
    private List<Doctor> doctors;

    public DoctorAdapter(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item, parent, false);
        return new DoctorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        holder.bind(doctors.get(position));
    }


    @Override
    public int getItemCount() {
        return doctors.size();
    }

    static class DoctorViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_username, tv_category, tv_rating;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_doctor_iv);
            tv_username = itemView.findViewById(R.id.item_doctor_tv_username);
            tv_category = itemView.findViewById(R.id.item_doctor_tv_category);
            tv_rating = itemView.findViewById(R.id.item_doctor_tv_rating);
        }

        private void bind(Doctor doctor) {
            if (doctor.getImage() == 0) {
                imageView.setImageResource(R.drawable.ic_user);
            } else {
                imageView.setImageResource(doctor.getImage());
            }
            tv_username.setText(doctor.getUsername());
            tv_category.setText(doctor.getCategory().getName());
            tv_rating.setText(String.valueOf(doctor.getRating()));
        }
    }
}
