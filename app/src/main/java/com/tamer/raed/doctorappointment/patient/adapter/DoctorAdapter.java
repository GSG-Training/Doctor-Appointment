package com.tamer.raed.doctorappointment.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Doctor;
import com.tamer.raed.doctorappointment.patient.interfaces.OnDoctorItemClickListener;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {
    private List<Doctor> doctors;
    private final OnDoctorItemClickListener listener;
    private final Context context;

    public DoctorAdapter(List<Doctor> doctors, OnDoctorItemClickListener listener, Context context) {
        this.doctors = doctors;
        this.listener = listener;
        this.context = context;
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

    class DoctorViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_username, tv_category, tv_phone;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_doctor_iv);
            tv_username = itemView.findViewById(R.id.item_doctor_tv_username);
            tv_category = itemView.findViewById(R.id.item_doctor_tv_category);
            tv_phone = itemView.findViewById(R.id.item_doctor_tv_phone);
        }

        private void bind(Doctor doctor) {
            String userId = doctor.getId();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            storageReference.child("profileImages/" + userId).getDownloadUrl().addOnSuccessListener(uri -> Glide.with(context)
                    .load(uri)
                    .into(imageView)).addOnFailureListener(exception -> Toast.makeText(context, context.getString(R.string.image_error), Toast.LENGTH_SHORT).show());
            tv_username.setText(doctor.getUsername());
            tv_category.setText(doctor.getSpecialization());
            tv_phone.setText(String.valueOf(doctor.getPhone()));
            itemView.setOnClickListener(view -> listener.onItemClick(doctor));
        }
    }
}
