package com.example.unifind.ui.presenter;

import android.util.Log;

import com.example.unifind.ui.model.Universities;
import com.example.unifind.ui.view.View;
import com.example.unifind.utils.ApiFactory;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UniversitiesPresenter {
    private CompositeDisposable compositeDisposable;
    com.example.unifind.ui.view.View.UniversitiesView view;

    public UniversitiesPresenter(View.UniversitiesView view) {
        this.view = view;
    }

    public void loadData(){
        view.ShowWait();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable=ApiFactory.getInstance().getApiService().getUniversities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Universities>>() {
                    @Override
                    public void accept(List<Universities> universities) throws Exception {
                        Log.d("Tag", "Ok");
                        view.ShowData(universities);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Tag", String.valueOf(throwable));
                        view.showError();
                    }
                });
        compositeDisposable.add(disposable);
        view.RemoveWait();
    }

    public void disposeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}

