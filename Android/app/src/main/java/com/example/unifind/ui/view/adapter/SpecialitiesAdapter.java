package com.example.unifind.ui.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unifind.R;
import com.example.unifind.ui.model.Specialities;

import java.util.List;

public class SpecialitiesAdapter extends RecyclerView.Adapter<SpecialitiesAdapter.SpecialitiesViewHolder> {
    private List<Specialities> specialities;
    private OnClicklistener onClicklistener;

    public SpecialitiesAdapter(List<Specialities> specialities, OnClicklistener onClicklistener) {
        this.specialities = specialities;
        this.onClicklistener = onClicklistener;
    }

    @NonNull
    @Override
    public SpecialitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.speciality_item,parent,false);
        return new SpecialitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialitiesViewHolder holder, int position) {
        Specialities speciality=specialities.get(position);
        holder.name_university.setText(speciality.getUni_name());
        holder.name_speciality.setText(speciality.getSpec_name());
        holder.ent.setText(String.valueOf(speciality.getENT()));
        holder.price.setText(speciality.getPrice());
        holder.code.setText(speciality.getCode());
    }

    @Override
    public int getItemCount() {
        return specialities.size();
    }

    public class SpecialitiesViewHolder extends RecyclerView.ViewHolder {
        TextView name_university,name_speciality,ent,price,code;
        public SpecialitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            name_university=itemView.findViewById(R.id.university_name);
            name_speciality=itemView.findViewById(R.id.speciality_name);
            code=itemView.findViewById(R.id.code_type);
            ent=itemView.findViewById(R.id.ent_type);
            price=itemView.findViewById(R.id.price_type);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClicklistener.onItemClick(v,getAdapterPosition());
                }
            });
        }
    }
}
