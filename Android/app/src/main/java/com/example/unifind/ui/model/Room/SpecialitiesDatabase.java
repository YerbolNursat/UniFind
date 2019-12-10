package com.example.unifind.ui.model.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Specialities.class, exportSchema = false,version = 1)
public abstract class SpecialitiesDatabase extends RoomDatabase {
    private static final  String DB_NAME="specialities_db";
    private static  SpecialitiesDatabase instance;
    public static synchronized SpecialitiesDatabase getInstance(Context context){
        if (instance== null){
            instance= Room.databaseBuilder(context.getApplicationContext(),SpecialitiesDatabase.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    public abstract SpecialitiesDao specialitiesDao();
}
