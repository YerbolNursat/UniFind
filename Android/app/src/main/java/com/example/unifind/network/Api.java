package com.example.unifind.network;


import com.example.unifind.constants.Constants;
import com.example.unifind.ui.model.Directions;
import com.example.unifind.ui.model.Professions;
import com.example.unifind.ui.model.Specialities;
import com.example.unifind.ui.model.Universities;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface Api {
    @GET("/universities/{lang}")
    Observable<List<Universities>> getUniversities(@Path("lang") String lang);
    @GET("/university/{id}/")
    Observable<List<Universities>> getUniversitiesbyID(@Path("id") int id);
    @GET("/get/{data}/{lang}")
    Observable<List<Directions>> getDirections(@Path("data") String data,@Path("lang") String lang);
    @GET("/professions/{direction}/{subject1}/{subject2}/{lang}")
    Observable<List<Professions>> getProfessions(@Path("direction") String direction,@Path("subject1") String subject1,@Path("subject2") String subject2,@Path("lang") String lang);
    @GET("/specialities/{professions}/{city}/{lang}")
    Observable<List<Specialities>> getSpecialities(@Path("professions") String professions,@Path("city") String city,@Path("lang") String lang);

}
