package com.stocard.weatherinfo.data.api

import com.stocard.weatherinfo.data.dto.WeatherInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created By Rafiqul Hasan
 */
interface WeatherInfoApi {
    @GET("onecall")
    suspend fun getWeatherInfo(@QueryMap queryMap: Map<String, String>): Response<WeatherInfoResponse>
}