package com.example.unifind.ui.model.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SpecialitiesDao {
    @Query("Select * from specialities")
    List<Specialities> getall();
    @Insert
    void insertSpeciality(Specialities specialities);
    @Delete
    void deleteSpeciality(Specialities specialities);

}
