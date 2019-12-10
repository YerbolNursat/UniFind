package com.example.unifind.ui.presenter;

import android.content.Context;

import com.example.unifind.ui.model.Room.Specialities;
import com.example.unifind.ui.model.Room.SpecialitiesDatabase;
import com.example.unifind.ui.view.View;
import com.example.unifind.utils.AppExecutors;

import java.util.List;
import java.util.concurrent.Executor;

public class UserPresenter {
    View.UserView userView;
    Context context;
    SpecialitiesDatabase database;
    List<Specialities> specialities;
    public UserPresenter(View.UserView userView, Context context) {
        this.userView = userView;
        this.context = context;
    }

    public void loadData(){
        database= SpecialitiesDatabase.getInstance(context);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                specialities=database.specialitiesDao().getall();
                userView.showdata(specialities);

            }
        });

    }

    public void deleteData(final Specialities speciality) {
        database= SpecialitiesDatabase.getInstance(context);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.specialitiesDao().deleteSpeciality(speciality);
            }
        });

    }
}
