package com.example.amit.cabtrackingdemo.POJO_Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amit on 19-12-2017.
 */

public class DistancenddurationPOJO3 {

    @SerializedName("distance")
    @Expose
    private DistancePOJO distance;
    @SerializedName("duration")
    @Expose
    private DurationPOJO duration;
    @SerializedName("status")
    @Expose
    private String status;

    public DistancePOJO getDistance() {
        return distance;
    }

    public void setDistance(DistancePOJO distance) {
        this.distance = distance;
    }

    public DurationPOJO getDuration() {
        return duration;
    }

    public void setDuration(DurationPOJO duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}//end of POJO3
