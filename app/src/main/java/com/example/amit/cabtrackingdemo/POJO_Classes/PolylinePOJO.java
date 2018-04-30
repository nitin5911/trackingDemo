package com.example.amit.cabtrackingdemo.POJO_Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amit on 20-12-2017.
 */

public class PolylinePOJO {
    @SerializedName("points")
    @Expose
    private String points;
    public String getPoints() {
        return points;
    }
    public void setPoints(String points) {
        this.points = points;
    }
}//end of POJO
