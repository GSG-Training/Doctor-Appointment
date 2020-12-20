package com.tamer.raed.doctorappointment.patient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    List<Category> categories;
    OnItemClickListener onItemClickListener;

    public CategoryAdapter(List<Category> categories, OnItemClickListener onItemClickListener) {
        this.categories = categories;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.category_item_iv);
        }

        private void bind(Category category) {
            imageView.setImageResource(category.getImage());
            itemView.setOnClickListener(view -> onItemClickListener.onClick(getAdapterPosition()));
        }
    }
}
