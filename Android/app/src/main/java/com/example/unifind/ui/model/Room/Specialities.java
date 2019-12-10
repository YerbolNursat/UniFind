package com.example.unifind.ui.model.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "specialities")
public class Specialities {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name ="Uniname")
    private String uniname;
    @ColumnInfo(name ="name")
    private String name;
    @Ignore
    public Specialities(int id, String uniname, String name) {
        this.id = id;
        this.uniname = uniname;
        this.name = name;
    }

    public Specialities(String uniname, String name) {
        this.uniname = uniname;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUniname() {
        return uniname;
    }

    public void setUniname(String uniname) {
        uniname = uniname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
