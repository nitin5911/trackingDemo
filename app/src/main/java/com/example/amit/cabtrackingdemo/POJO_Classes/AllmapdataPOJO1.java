package com.example.amit.cabtrackingdemo.POJO_Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllmapdataPOJO1 {
    @SerializedName("geocoded_waypoints")
    @Expose
    private List<AllmapdatageowaypointsPOJO2> geocodedWaypoints = null;
    @SerializedName("routes")
    @Expose
    private List<AllmapdataroutePOJO3> routes = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<AllmapdatageowaypointsPOJO2> getGeocodedWaypoints() {
        return geocodedWaypoints;
    }

    public void setGeocodedWaypoints(List<AllmapdatageowaypointsPOJO2> geocodedWaypoints) {
        this.geocodedWaypoints = geocodedWaypoints;
    }

    public List<AllmapdataroutePOJO3> getRoutes() {
        return routes;
    }

    public void setRoutes(List<AllmapdataroutePOJO3> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}//end of all map data POJO1
