package com.example.unifind.ui.presenter;

import android.content.Context;
import android.util.Log;

import com.example.unifind.R;
import com.example.unifind.constants.Constants;
import com.example.unifind.ui.model.Directions;
import com.example.unifind.ui.model.Professions;
import com.example.unifind.ui.model.Room.SpecialitiesDatabase;
import com.example.unifind.ui.model.Specialities;
import com.example.unifind.ui.view.View;
import com.example.unifind.utils.ApiFactory;
import com.example.unifind.utils.AppExecutors;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class PickerPresenter {
    private CompositeDisposable compositeDisposable;
    View.ChooserPage view;
    SpecialitiesDatabase database;
    Context context;
    List<String> questions;
    public PickerPresenter(View.ChooserPage view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void loadDirections(String data) {
        view.ShowWait();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = ApiFactory.getInstance().getApiService()
                .getDirections(data, Constants.LANG)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Directions>>() {
                    @Override
                    public void accept(List<Directions> directions) throws Exception {
                        view.ShowDirections(directions);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Tag", String.valueOf(throwable));
                        //view.showError();
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void loadQuestions(Context context) {
        if (Constants.LANG.equals("KZ")) {
            questions = Arrays.asList(context.getResources().getStringArray(R.array.Questions_KZ));
        } else if (Constants.LANG.equals("ENG")) {
            questions = Arrays.asList(context.getResources().getStringArray(R.array.Questions_ENG));
        } else {
            questions = Arrays.asList(context.getResources().getStringArray(R.array.Questions));
        }
        view.ShowQuestions(questions);
    }

    public void loadProfessions(String direction, String subject1, String subject2) {
        view.ShowWait();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = ApiFactory.getInstance().getApiService()
                .getProfessions(direction, subject1, subject2, Constants.LANG)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Professions>>() {
                    @Override
                    public void accept(List<Professions> professions) throws Exception {
                        view.ShowProfessions(professions);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Tag", String.valueOf(throwable));
                        //view.showError();
                    }
                });
        compositeDisposable.add(disposable);

    }

    public void loadSpecialities(String professions, String city) {
        view.ShowWait();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = ApiFactory.getInstance().getApiService()
                .getSpecialities(professions, city, Constants.LANG)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Specialities>>() {
                    @Override
                    public void accept(List<Specialities> specialities) throws Exception {

                        view.ShowSpecialities(specialities);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Tag", String.valueOf(throwable));
                        //view.showError();
                    }
                });
        compositeDisposable.add(disposable);


    }

    public void setdata(final List<com.example.unifind.ui.model.Room.Specialities> specialities) {
        database = SpecialitiesDatabase.getInstance(context);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                for (com.example.unifind.ui.model.Room.Specialities speciality : specialities) {
                    database.specialitiesDao().insertSpeciality(speciality);
                }
                view.makeIntent();
            }
        });
    }
}
