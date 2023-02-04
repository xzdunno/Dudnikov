package com.example.dudnikov.data.network

import com.example.dudnikov.BuildConfig
import com.example.dudnikov.data.model.Description
import com.example.dudnikov.data.model.Top100Data
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface RetroInterface {
    @Headers("X-API-KEY:${BuildConfig.API_Key}")
    @GET("api/v2.2/films/top?")
    suspend fun getTop100(@QueryMap opt:MutableMap<String,String>): Top100Data

    @Headers("X-API-KEY:${BuildConfig.API_Key}")
    @GET("api/v2.2/films/{id}")
    suspend fun getDescription(@Path("id") id: String): Description
}