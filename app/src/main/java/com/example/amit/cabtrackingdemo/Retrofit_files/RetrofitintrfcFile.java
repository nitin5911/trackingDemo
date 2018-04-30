package com.example.amit.cabtrackingdemo.Retrofit_files;

import com.example.amit.cabtrackingdemo.POJO_Classes.AllmapdataPOJO1;
import com.example.amit.cabtrackingdemo.POJO_Classes.DistancenddurationPOJO1;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Amit on 19-12-2017.
 */

public interface RetrofitintrfcFile {

    @GET("distancematrix/json")
    Call<DistancenddurationPOJO1> distdurtninterface_method(@Query("origins") String originlocation,
                                                            @Query("destinations") String destlocation,
                                                            @Query("key") String apikey);

    @GET("directions/json")
    Call<AllmapdataPOJO1> allapidataintrfc_method(@Query("origin") String originlocation,
                                                  @Query("destination") String destlocation,
                                                  @Query("key") String apikey,
                                                  @Query("alternatives") boolean alternatevalue);


    @GET("directions/json")
    Call<AllmapdataPOJO1> trackapiintrfc_method(@Query("origin") String originlocation,
                                                  @Query("destination") String destlocation,
                                                  @Query("key") String apikey);



}//end of interface file
