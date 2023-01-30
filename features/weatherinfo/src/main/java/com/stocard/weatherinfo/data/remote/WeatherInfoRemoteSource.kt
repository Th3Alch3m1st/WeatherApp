package com.stocard.weatherinfo.data.remote

import com.stocard.core.network.Resource
import com.stocard.weatherinfo.data.dto.WeatherInfoResponse

/**
 * Created By Rafiqul Hasan
 */
interface WeatherInfoRemoteSource {
	suspend fun getWeatherInfo(latitude: Double, longitude: Double,unitType:String): Resource<WeatherInfoResponse>
}