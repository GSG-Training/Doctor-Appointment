package com.tamer.raed.doctorappointment.patient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private final List<Category> categories;
    private final OnItemClickListener onItemClickListener;
    private final Context context;
    private int position;

    public CategoryAdapter(Context context, List<Category> categories, OnItemClickListener onItemClickListener) {
        this.categories = categories;
        this.onItemClickListener = onItemClickListener;
        this.context = context;
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

    public int getPosition() {
        return position;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.category_item_iv);
        }

        private void bind(Category category) {
            position = getAdapterPosition();

            imageView.setImageResource(category.getImage());
            if (category.getState()) {
                imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.category_bg_on));
                imageView.setColorFilter(context.getResources().getColor(R.color.white));
            } else {
                imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.category_bg_off));
                imageView.setColorFilter(context.getResources().getColor(R.color.colorPrimary));
            }

            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(getBindingAdapterPosition()));
        }
    }
}
