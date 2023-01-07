package com.example.asteroidradar.api

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("neo/rest/v1/feed")
    fun getNearAsteroids(@Query("api_key")key: String, @Query("end_date")endDate: String,
                                 @Query("start_date")startDate: String): Call<String>

    @GET("planetary/apod")
    fun getDayImage(@Query("api_key")key: String): Call<ImageModel>

}