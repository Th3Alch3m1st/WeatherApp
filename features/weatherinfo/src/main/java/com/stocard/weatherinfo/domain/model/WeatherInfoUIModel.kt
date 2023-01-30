package com.stocard.weatherinfo.domain.model

import com.stocard.core.model.UnitMeasurement

/**
 * Created By Rafiqul Hasan
 */
data class WeatherInfoUIModel(
	val currentWeatherInfo: CurrentWeatherInfo,
	val hourly: List<WeatherInfoHourly>,
	val daily: List<WeatherInfoDaily>
)

data class WeatherInfoHourly(
	val dateTime: String,
	val temp: Float,
	val humidity: Int,
	val weather: WeatherInfoIcon,
	val type: UnitMeasurement
)

data class WeatherInfoDaily(
	val dateTime: String,
	val temp: Temperature,
	val humidity: Int,
	val weather: WeatherInfoIcon,
	val type: UnitMeasurement
)

data class WeatherInfoIcon(
	val description: String,
	val icon: String
)

data class Temperature(
	val min: Float,
	val max: Float,
	val type: UnitMeasurement
)
class CurrentWeatherInfo(
	val location: String,
	val time: String,
	val temp: Float,
	val humidity: Int,
	val windSpeed: Float,
	val feelsLike: Float,
	val weatherIcon: WeatherInfoIcon,
	val type: UnitMeasurement = UnitMeasurement.METRIC
)