package com.example.unifind.network;

import androidx.lifecycle.LiveData;

import com.example.unifind.ui.model.Universities;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface Api {
    @GET("/universities")
    Observable<List<Universities>> getUniversities();

    @GET("/university/{id}")
    Call<List<Universities>> getUniversitiesbyID(@Path("id") int id);
}
