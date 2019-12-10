package com.example.unifind.ui.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unifind.R;
import com.example.unifind.ui.model.Professions;

import java.util.List;

public class ProfessionsAdapter  extends RecyclerView.Adapter<ProfessionsAdapter.ProfessionViewHolder> {
    private List<Professions> Allprofessions;
    private List<Professions> likedprofessions;
    private OnClicklistener onClicklistener;

    public ProfessionsAdapter(List<Professions> allprofessions, OnClicklistener onClicklistener) {
        this.Allprofessions = allprofessions;
        this.onClicklistener = onClicklistener;
    }

    @NonNull
    @Override
    public ProfessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profession_item,parent,false);
        return new ProfessionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProfessionViewHolder holder, int position) {
        holder.name.setText(Allprofessions.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return Allprofessions.size();
    }

    public class ProfessionViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public ProfessionViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.profession_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClicklistener.onItemClick(v,getAdapterPosition());
                }
            });
        }
    }
}
