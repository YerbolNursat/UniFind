package com.example.unifind.ui.view;


import com.example.unifind.ui.model.Directions;
import com.example.unifind.ui.model.Professions;
import com.example.unifind.ui.model.Specialities;
import com.example.unifind.ui.model.Universities;

import java.util.List;

public interface View {


    interface UniversitiesView {
        void ShowData(List<Universities> universities);

        void ShowWait();

        void RemoveWait();

        void showError();

        void initialize();
    }

    interface ChooserPage {
        void ShowQuestions(List<String> questions);

        void ShowDirections(List<Directions> universities);

        void ShowProfessions(List<Professions> professions);

        void ShowSpecialities(List<Specialities> specialities);

        void initialize();

        void ShowWait();

        void RemoveWait();

        void makeToast(String text);

        void makeIntent();
    }

    interface UserView {
        void initialise();
        void showdata(List<com.example.unifind.ui.model.Room.Specialities> specialities);
    }

    interface LoginPage {
        void signIn();

        void initialize();

        void Intent();

        void MakeToast(String text);
    }

}
