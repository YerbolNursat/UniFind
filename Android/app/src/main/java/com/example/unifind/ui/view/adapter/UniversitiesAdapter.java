package com.example.unifind.ui.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unifind.R;
import com.example.unifind.ui.model.Universities;

import java.util.ArrayList;
import java.util.List;

public class UniversitiesAdapter extends RecyclerView.Adapter<UniversitiesAdapter.UniversitiesViewHolder> implements Filterable {
    private List<Universities> universities;
    private List<Universities> universitiesFull;

    public UniversitiesAdapter(List<Universities> universities) {
        this.universities = universities;
        universitiesFull = new ArrayList<>(universities);
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
        if (university.getDormitory() == 1) {
            holder.sub_items.setText("Есть");
        } else {
            holder.sub_items.setText("Нет");
        }

    }

    @Override
    public int getItemCount() {
        return universities.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Universities> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(universitiesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim().replaceAll(" ", "");
                for (Universities item : universitiesFull) {
                    if (item.getName().toLowerCase().trim().replaceAll(" ", "").contains(filterPattern)) {
                        if (!(filteredList.contains(item))) {
                            filteredList.add(item);
                        }
                    }

                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            universities.clear();
            universities.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


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
