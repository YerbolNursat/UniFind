package com.example.unifind.ui.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unifind.R;
import com.example.unifind.ui.model.Universities;

import java.util.ArrayList;
import java.util.List;

public class UniversitiesAdapter extends RecyclerView.Adapter<UniversitiesAdapter.UniversitiesViewHolder> {
    private List<Universities> universities;

    public UniversitiesAdapter(List<Universities> universities) {
        this.universities = universities;
    }


    @NonNull
    @Override
    public UniversitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.university_item, parent, false);
        return new UniversitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversitiesViewHolder holder, int position) {
        Universities university = universities.get(position);
        holder.name.setText(university.getName());
        holder.code_info.setText(university.getCode());
        holder.sub_items.setText(university.getDescription());

    }

    @Override
    public int getItemCount() {
        return universities.size();
    }

    public class UniversitiesViewHolder extends RecyclerView.ViewHolder {
        TextView name, code_info, sub_items;

        public UniversitiesViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.university_name);
            code_info = itemView.findViewById(R.id.university_code_type);
            sub_items = itemView.findViewById(R.id.university_sub_info);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Hello World", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
