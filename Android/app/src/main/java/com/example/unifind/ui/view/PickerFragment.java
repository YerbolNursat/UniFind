package com.example.unifind.ui.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unifind.R;
import com.example.unifind.ui.model.Directions;
import com.example.unifind.ui.model.Professions;
import com.example.unifind.ui.presenter.PickerPresenter;
import com.example.unifind.ui.view.adapter.DirectionsAdapter;
import com.example.unifind.ui.view.adapter.OnClicklistener;
import com.example.unifind.ui.view.adapter.ProfessionsAdapter;
import com.example.unifind.ui.view.adapter.QuestionAdapter;

import java.util.List;

import static android.content.ContentValues.TAG;

public class PickerFragment extends Fragment implements com.example.unifind.ui.view.View.ChooserPage {
    View root;
    RecyclerView directionrv,questionrv,professionrv;
    DirectionsAdapter directionadapter;
    QuestionAdapter questionAdapter;
    ProfessionsAdapter professionsAdapter;
    PickerPresenter presenter;
    String data;
    ImageView next;
    LinearLayout firstpage,secondpage,thirdpage,fourth_page;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.chooser_page, container, false);
        initialize();
        data="9,5,3,5,7,6,8,5,4,6,3,6,7,8,10,2,3,6,7,8,10,8,10,2,4,5,6,7,8,9,4,3,4,5,6,8,9,10,10,3,2";
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstpage.getVisibility()==View.VISIBLE){
                    firstpage.setVisibility(View.GONE);
                    presenter.loadQuestions(root.getContext());
                    secondpage.setVisibility(View.VISIBLE);
                }else if (secondpage.getVisibility()==View.VISIBLE){
                    secondpage.setVisibility(View.GONE);
                    presenter.loadDirections(data);
                    next.setVisibility(View.GONE);
                    thirdpage.setVisibility(View.VISIBLE);
                }
            }
        });
        return root;
    }

    @Override
    public void ShowQuestions(List<String> questions) {
        questionAdapter=new QuestionAdapter(questions);
        questionrv.setAdapter(questionAdapter);

    }

    @Override
    public void ShowDirections(final List<Directions> directions) {
        directionadapter = new DirectionsAdapter(directions, new OnClicklistener() {
            @Override
            public void onItemClick(View view, int position) {
                thirdpage.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
                fourth_page.setVisibility(View.VISIBLE);
                presenter.loadProfessions(directions.get(position).getName());
            }
        });
        directionrv.setAdapter(directionadapter);

    }


    @Override
    public void ShowProfessions(List<Professions> professions) {

        professionsAdapter=new ProfessionsAdapter(professions, new OnClicklistener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(view.getContext(), "Hello World", Toast.LENGTH_SHORT).show();
            }
        });
        professionrv.setAdapter(professionsAdapter);
    }

    @Override
    public void ShowSpecialities() {

    }

    @Override
    public void initialize() {
        directionrv=root.findViewById(R.id.directions_rv);
        questionrv=root.findViewById(R.id.questions_rv);
        professionrv=root.findViewById(R.id.profession_rv);
        presenter = new PickerPresenter(this);
        next=root.findViewById(R.id.next);
        firstpage=root.findViewById(R.id.first_page);
        secondpage=root.findViewById(R.id.second_page);
        thirdpage=root.findViewById(R.id.third_page);
        fourth_page=root.findViewById(R.id.fourth_page);
        questionrv.setHasFixedSize(true);
        directionrv.setHasFixedSize(true);
        professionrv.setHasFixedSize(true);
        professionrv.setLayoutManager(new LinearLayoutManager(root.getContext()));
        directionrv.setLayoutManager(new LinearLayoutManager(root.getContext()));
        questionrv.setLayoutManager(new LinearLayoutManager(root.getContext()));

    }
}
