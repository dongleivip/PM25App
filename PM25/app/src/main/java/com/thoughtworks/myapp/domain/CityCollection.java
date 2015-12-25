package com.thoughtworks.myapp.domain;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


/**
 * Created by dl on 2015/12/25.
 */
public class CityCollection {

    @SerializedName("cities")
    private ArrayList<String> cities;

    public ArrayList<String> getCities() {
        return cities;
    }

    public void setCities(ArrayList<String> cities) {
        this.cities = cities;
    }
}
