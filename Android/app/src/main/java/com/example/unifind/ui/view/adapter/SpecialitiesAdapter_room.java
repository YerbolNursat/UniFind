package com.example.unifind.ui.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unifind.R;
import com.example.unifind.ui.model.Room.Specialities;

import java.util.List;

import static android.content.ContentValues.TAG;

public class SpecialitiesAdapter_room extends RecyclerView.Adapter<SpecialitiesAdapter_room.SpecialitiesAdapter_roomViewHolder> {
    private List<Specialities> specialities;
    private OnClicklistener onClicklistener;

    public SpecialitiesAdapter_room(List<Specialities> specialities, OnClicklistener onClicklistener) {
        this.specialities = specialities;
        this.onClicklistener = onClicklistener;
    }

    @NonNull
    @Override
    public SpecialitiesAdapter_roomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.specialities_item,parent,false);
        return new SpecialitiesAdapter_roomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialitiesAdapter_roomViewHolder holder, int position) {
        Specialities speciality=specialities.get(position);
        holder.speciality_name.setText(speciality.getName());
        holder.university_name.setText(speciality.getUniname());

    }

    @Override
    public int getItemCount() {
        return specialities.size();
    }

    public class SpecialitiesAdapter_roomViewHolder extends RecyclerView.ViewHolder {
        TextView university_name,speciality_name;
        public SpecialitiesAdapter_roomViewHolder(@NonNull final View itemView) {
            super(itemView);
            university_name=itemView.findViewById(R.id.university_name);
            speciality_name=itemView.findViewById(R.id.speciality_name);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onClicklistener.onItemClick(itemView,getAdapterPosition());
                    return false;
                }
            });

        }
    }
}
