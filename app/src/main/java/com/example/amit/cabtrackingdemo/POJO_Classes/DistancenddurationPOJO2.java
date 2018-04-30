package com.example.amit.cabtrackingdemo.POJO_Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Amit on 19-12-2017.
 */

public class DistancenddurationPOJO2 {

    @SerializedName("elements")
    @Expose
    private List<DistancenddurationPOJO3> elements = null;

    public List<DistancenddurationPOJO3> getElements() {
        return elements;
    }

    public void setElements(List<DistancenddurationPOJO3> elements) {
        this.elements = elements;
    }



}//end of POJO2
