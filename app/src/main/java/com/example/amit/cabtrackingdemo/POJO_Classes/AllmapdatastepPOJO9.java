package com.example.amit.cabtrackingdemo.POJO_Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amit on 20-12-2017.
 */

public class AllmapdatastepPOJO9 {
    @SerializedName("distance")
    @Expose
    private DistancePOJO distance;
    @SerializedName("duration")
    @Expose
    private DurationPOJO duration;
    @SerializedName("end_location")
    @Expose
    private EndLocationPOJO endLocation;
    @SerializedName("html_instructions")
    @Expose
    private String htmlInstructions;
    @SerializedName("polyline")
    @Expose
    private PolylinePOJO polyline;
    @SerializedName("start_location")
    @Expose
    private StartLocationPOJO startLocation;
    @SerializedName("travel_mode")
    @Expose
    private String travelMode;
    @SerializedName("maneuver")
    @Expose
    private String maneuver;

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

    public EndLocationPOJO getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(EndLocationPOJO endLocation) {
        this.endLocation = endLocation;
    }

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public void setHtmlInstructions(String htmlInstructions) {
        this.htmlInstructions = htmlInstructions;
    }

    public PolylinePOJO getPolyline() {
        return polyline;
    }

    public void setPolyline(PolylinePOJO polyline) {
        this.polyline = polyline;
    }

    public StartLocationPOJO getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(StartLocationPOJO startLocation) {
        this.startLocation = startLocation;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public String getManeuver() {
        return maneuver;
    }

    public void setManeuver(String maneuver) {
        this.maneuver = maneuver;
    }
}//end of POJO9
