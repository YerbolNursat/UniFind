package com.example.unifind.ui.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Universities {
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Dormitory")
    @Expose
    private Integer Dormitory;

    public Universities(String name, String code, Integer dormitory) {
        this.name = name;
        this.code = code;
        this.Dormitory = dormitory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDormitory() {
        return Dormitory;
    }

    public void setDormitory(Integer dormitory) {
        Dormitory = dormitory;
    }
}
