package com.example.amit.cabtrackingdemo.POJO_Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllmapdataroutePOJO3 {

    @SerializedName("bounds")
    @Expose
    private AllmapdataboundsPOJO4 bounds;
    @SerializedName("copyrights")
    @Expose
    private String copyrights;
    @SerializedName("legs")
    @Expose
    private List<AllmapdatalegsPOJO5> legs = null;
    @SerializedName("overview_polyline")
    @Expose
    private PolylinePOJO overviewPolyline;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("warnings")
    @Expose
    private List<String> warnings = null;
    @SerializedName("waypoint_order")
    @Expose
    private List<Object> waypointOrder = null;

    public AllmapdataboundsPOJO4 getBounds() {
        return bounds;
    }

    public void setBounds(AllmapdataboundsPOJO4 bounds) {
        this.bounds = bounds;
    }

    public String getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public List<AllmapdatalegsPOJO5> getLegs() {
        return legs;
    }

    public void setLegs(List<AllmapdatalegsPOJO5> legs) {
        this.legs = legs;
    }

    public PolylinePOJO getOverviewPolyline() {
        return overviewPolyline;
    }

    public void setOverviewPolyline(PolylinePOJO overviewPolyline) {
        this.overviewPolyline = overviewPolyline;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public List<Object> getWaypointOrder() {
        return waypointOrder;
    }

    public void setWaypointOrder(List<Object> waypointOrder) {
        this.waypointOrder = waypointOrder;
    }


}//end of POJO3
