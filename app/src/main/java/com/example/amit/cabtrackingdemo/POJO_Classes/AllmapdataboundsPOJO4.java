package com.example.amit.cabtrackingdemo.POJO_Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amit on 20-12-2017.
 */

public class AllmapdataboundsPOJO4 {

    @SerializedName("northeast")
    @Expose
    private AllmapdataboundsnorthPOJO7 northeast;
    @SerializedName("southwest")
    @Expose
    private AllmapdataboundssouthPOJO8 southwest;

    public AllmapdataboundsnorthPOJO7 getNortheast() {
        return northeast;
    }
    public void setNortheast(AllmapdataboundsnorthPOJO7 northeast) {
        this.northeast = northeast;
    }
    public AllmapdataboundssouthPOJO8 getSouthwest() {
        return southwest;
    }
    public void setSouthwest(AllmapdataboundssouthPOJO8 southwest) {
        this.southwest = southwest;
    }
}//end of POJO4
