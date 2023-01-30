package com.stocard.weatherinfo.domain.usecase

import com.stocard.core.network.Resource
import com.stocard.core.model.UnitMeasurement
import com.stocard.weatherinfo.domain.model.WeatherInfoUIModel

/**
 * Created By Rafiqul Hasan
 */
interface WeatherInfoUseCase {
	suspend fun getWeatherInfo(latitude: Double, longitude: Double,unitType: UnitMeasurement): Resource<WeatherInfoUIModel>
}