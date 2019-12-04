package com.example.unifind.ui.view;


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

    interface UserView {

    }

    interface LoginPage {
        void signIn();

        void initialize();

        void Intent();

        void MakeToast(String text);
    }

}
