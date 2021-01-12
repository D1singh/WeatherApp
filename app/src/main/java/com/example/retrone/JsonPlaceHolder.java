package com.example.retrone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolder {

    @GET("current.json")
    Call<WeatherOne> getdata(
            @Query("key")String key,
            @Query("q") String name
            );

    @GET("astronomy.json")
    Call<WeatherAstronomy> getAstronomyDetails(
            @Query("key")String key,
            @Query("q") String name,
            @Query("dt") String date
    );
}
