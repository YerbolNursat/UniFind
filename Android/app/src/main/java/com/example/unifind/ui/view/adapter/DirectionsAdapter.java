package com.example.unifind.ui.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unifind.R;
import com.example.unifind.ui.model.Directions;
import com.timqi.sectorprogressview.ColorfulRingProgressView;

import java.util.List;


public class DirectionsAdapter extends RecyclerView.Adapter<DirectionsAdapter.DirectionsViewHolder> {
    private List<Directions> directions;
    private OnClicklistener onClicklistener;

    public DirectionsAdapter(List<Directions> directions, OnClicklistener onClicklistener) {
        this.directions = directions;
        this.onClicklistener = onClicklistener;
    }

    @NonNull
    @Override
    public DirectionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.direction_item, parent, false);
        return new DirectionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectionsViewHolder holder, int position) {
        Directions direction = directions.get(position);
        holder.progressbar.setPercent(Float.valueOf(direction.getScore()));
        holder.percent.setText(String.valueOf(direction.getScore()));
        holder.name.setText(direction.getName());
        holder.description.setText(direction.getDescription());
    }

    @Override
    public int getItemCount() {
        return directions.size();
    }

    public class DirectionsViewHolder extends RecyclerView.ViewHolder {
        TextView percent, description, name;
        ColorfulRingProgressView progressbar;

        public DirectionsViewHolder(@NonNull final View view) {
            super(view);
            percent = view.findViewById(R.id.text_percent);
            description = view.findViewById(R.id.description_of_direction);
            name = view.findViewById(R.id.name_of_direction);
            progressbar = view.findViewById(R.id.crpv);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClicklistener.onItemClick(itemView,getAdapterPosition());
                }
            });
        }
    }
}
