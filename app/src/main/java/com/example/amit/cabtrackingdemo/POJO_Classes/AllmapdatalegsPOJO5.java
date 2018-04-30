package com.example.amit.cabtrackingdemo.POJO_Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AllmapdatalegsPOJO5 {

    @SerializedName("distance")
    @Expose
    private DistancePOJO distance;
    @SerializedName("duration")
    @Expose
    private DurationPOJO duration;
    @SerializedName("end_address")
    @Expose
    private String endAddress;
    @SerializedName("end_location")
    @Expose
    private EndLocationPOJO endLocationPOJO;
    @SerializedName("start_address")
    @Expose
    private String startAddress;
    @SerializedName("start_location")
    @Expose
    private StartLocationPOJO startLocationPOJO;
    @SerializedName("steps")
    @Expose
    private List<AllmapdatastepPOJO9> steps = null;
    @SerializedName("traffic_speed_entry")
    @Expose
    private List<Object> trafficSpeedEntry = null;
    @SerializedName("via_waypoint")
    @Expose
    private List<Object> viaWaypoint = null;

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

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public EndLocationPOJO getEndLocationPOJO() {
        return endLocationPOJO;
    }

    public void setEndLocationPOJO(EndLocationPOJO endLocationPOJO) {
        this.endLocationPOJO = endLocationPOJO;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public StartLocationPOJO getStartLocationPOJO() {
        return startLocationPOJO;
    }

    public void setStartLocationPOJO(StartLocationPOJO startLocationPOJO) {
        this.startLocationPOJO = startLocationPOJO;
    }

    public List<AllmapdatastepPOJO9> getSteps() {
        return steps;
    }

    public void setSteps(List<AllmapdatastepPOJO9> steps) {
        this.steps = steps;
    }

    public List<Object> getTrafficSpeedEntry() {
        return trafficSpeedEntry;
    }

    public void setTrafficSpeedEntry(List<Object> trafficSpeedEntry) {
        this.trafficSpeedEntry = trafficSpeedEntry;
    }

    public List<Object> getViaWaypoint() {
        return viaWaypoint;
    }

    public void setViaWaypoint(List<Object> viaWaypoint) {
        this.viaWaypoint = viaWaypoint;
    }
}//end of POJO5
