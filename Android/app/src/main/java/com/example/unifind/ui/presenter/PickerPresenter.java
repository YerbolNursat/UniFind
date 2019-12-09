package com.example.unifind.ui.presenter;

import android.content.Context;
import android.util.Log;

import com.example.unifind.R;
import com.example.unifind.ui.model.Directions;
import com.example.unifind.ui.model.Professions;
import com.example.unifind.ui.view.View;
import com.example.unifind.utils.ApiFactory;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PickerPresenter {
    private CompositeDisposable compositeDisposable;
    View.ChooserPage view;

    public PickerPresenter(View.ChooserPage chooserPage) {
        this.view = chooserPage;
    }

    public void loadDirections(String data) {
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = ApiFactory.getInstance().getApiService()
                .getDirections(data)
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
        List<String> questions = Arrays.asList(context.getResources().getStringArray(R.array.Questions));
        view.ShowQuestions(questions);
    }
    public void loadProfessions(String direction){
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = ApiFactory.getInstance().getApiService()
                .getProfessions(direction)
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
}
