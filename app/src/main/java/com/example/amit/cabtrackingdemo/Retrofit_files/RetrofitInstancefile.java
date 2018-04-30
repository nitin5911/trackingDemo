package com.example.amit.cabtrackingdemo.Retrofit_files;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Amit on 19-12-2017.
 */

public class RetrofitInstancefile {

    public static final String base_url="https://maps.googleapis.com/maps/api/";
    public static Retrofit retrofit_obj=null;

    public static Retrofit retrofit_method(){
        if(retrofit_obj==null){
            retrofit_obj= new Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create()).build();
        }//end of if
        return retrofit_obj;
    }//end of method


}//end of Retrofit instance file
