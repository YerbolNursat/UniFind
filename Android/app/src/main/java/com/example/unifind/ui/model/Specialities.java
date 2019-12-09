package com.example.unifind.ui.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Specialities {
    @SerializedName("Uni_name")
    @Expose
    private String Uni_name;
    @SerializedName("Spec_name")
    @Expose
    private String Spec_name;
    @SerializedName("Code")
    @Expose
    private String Code;
    @SerializedName("ENT")
    @Expose
    private Integer ENT;
    @SerializedName("Price")
    @Expose
    private String Price;

    public Specialities(String uni_name, String spec_name, String code, Integer ENT, String price) {
        Uni_name = uni_name;
        Spec_name = spec_name;
        Code = code;
        this.ENT = ENT;
        Price = price;
    }

    public String getUni_name() {
        return Uni_name;
    }

    public void setUni_name(String uni_name) {
        Uni_name = uni_name;
    }

    public String getSpec_name() {
        return Spec_name;
    }

    public void setSpec_name(String spec_name) {
        Spec_name = spec_name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public Integer getENT() {
        return ENT;
    }

    public void setENT(Integer ENT) {
        this.ENT = ENT;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
