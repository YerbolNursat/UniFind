package com.example.unifind.network;


import com.example.unifind.ui.model.Directions;
import com.example.unifind.ui.model.Professions;
import com.example.unifind.ui.model.Specialities;
import com.example.unifind.ui.model.Universities;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface Api {
    @GET("/universities")
    Observable<List<Universities>> getUniversities();

    @GET("/university/{id}")
    Observable<List<Universities>> getUniversitiesbyID(@Path("id") int id);
    @GET("/get/{data}")
    Observable<List<Directions>> getDirections(@Path("data") String data);
    @GET("/professions/{direction}")
    Observable<List<Professions>> getProfessions(@Path("direction") String direction);
    @GET("/specialities/{professions}")
    Observable<List<Specialities>> getSpecialities(@Path("professions") String professions);

}
