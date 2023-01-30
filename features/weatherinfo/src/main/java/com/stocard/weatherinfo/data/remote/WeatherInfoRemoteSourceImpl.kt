package com.stocard.weatherinfo.data.remote

import com.stocard.core.BuildConfig
import com.stocard.core.di.qualifiers.IoDispatcher
import com.stocard.core.network.BaseSource
import com.stocard.core.network.Resource
import com.stocard.weatherinfo.data.api.WeatherInfoApi
import com.stocard.weatherinfo.data.dto.WeatherInfoResponse
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created By Rafiqul Hasan
 */
class WeatherInfoRemoteSourceImpl @Inject constructor(
	private val api: WeatherInfoApi,
	@IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : WeatherInfoRemoteSource, BaseSource() {
	override suspend fun getWeatherInfo(
		latitude: Double,
		longitude: Double,
		unitType: String
	): Resource<WeatherInfoResponse> {
		val queryMap = mapOf(
			"lat" to "$latitude",
			"lon" to "$longitude",
			"exclude" to "minutely",
			"appid" to BuildConfig.AUTH_TOKEN,
			"units" to unitType,
		)
		return safeApiCall(ioDispatcher){
			api.getWeatherInfo(queryMap)
		}
	}
}