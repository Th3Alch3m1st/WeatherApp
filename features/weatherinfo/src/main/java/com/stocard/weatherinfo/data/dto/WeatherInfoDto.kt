package com.stocard.weatherinfo.data.dto

import com.stocard.core.model.UnitMeasurement
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created By Rafiqul Hasan
 */
@JsonClass(generateAdapter = true)
data class WeatherInfoResponse(

	@Json(name="timezone")
	val timezone: String? = null,

	@Json(name="timezone_offset")
	val timezoneOffset: Int? = null,

	@Json(name="current")
	val current: Current? = null,

	@Json(name="daily")
	val daily: List<DailyItem>? = null,

	@Json(name="lon")
	val lon: Double? = null,

	@Json(name="hourly")
	val hourly: List<HourlyItem>? = null,

	@Json(name="lat")
	val lat: Double? = null
){
	@Transient
	lateinit var unitMeasurement: UnitMeasurement
}

@JsonClass(generateAdapter = true)
data class DailyItem(

	@Json(name="moonset")
	val moonset: Int? = null,

	@Json(name="rain")
	val rain: Double? = null,

	@Json(name="sunrise")
	val sunrise: Int? = null,

	@Json(name="temp")
	val temp: Temp? = null,

	@Json(name="moon_phase")
	val moonPhase: Double? = null,

	@Json(name="uvi")
	val uvi: Double? = null,

	@Json(name="moonrise")
	val moonrise: Int? = null,

	@Json(name="pressure")
	val pressure: Int? = null,

	@Json(name="clouds")
	val clouds: Int? = null,

	@Json(name="feels_like")
	val feelsLike: FeelsLike? = null,

	@Json(name="wind_gust")
	val windGust: Double? = null,

	@Json(name="dt")
	val dt: Long? = null,

	@Json(name="pop")
	val pop: Double? = null,

	@Json(name="wind_deg")
	val windDeg: Int? = null,

	@Json(name="dew_point")
	val dewPoint: Double? = null,

	@Json(name="sunset")
	val sunset: Int? = null,

	@Json(name="weather")
	val weather: List<WeatherItem?>? = null,

	@Json(name="humidity")
	val humidity: Int? = null,

	@Json(name="wind_speed")
	val windSpeed: Double? = null
)

@JsonClass(generateAdapter = true)
data class Temp(

	@Json(name="min")
	val min: Double? = null,

	@Json(name="max")
	val max: Double? = null,

	@Json(name="eve")
	val eve: Double? = null,

	@Json(name="night")
	val night: Double? = null,

	@Json(name="day")
	val day: Double? = null,

	@Json(name="morn")
	val morn: Double? = null
)

@JsonClass(generateAdapter = true)
data class HourlyItem(

	@Json(name="temp")
	val temp: Double? = null,

	@Json(name="visibility")
	val visibility: Int? = null,

	@Json(name="uvi")
	val uvi: Double? = null,

	@Json(name="pressure")
	val pressure: Int? = null,

	@Json(name="clouds")
	val clouds: Int? = null,

	@Json(name="feels_like")
	val feelsLike: Double? = null,

	@Json(name="wind_gust")
	val windGust: Double? = null,

	@Json(name="dt")
	val dt: Long? = null,

	@Json(name="pop")
	val pop: Double? = null,

	@Json(name="wind_deg")
	val windDeg: Int? = null,

	@Json(name="dew_point")
	val dewPoint: Double? = null,

	@Json(name="weather")
	val weather: List<WeatherItem>? = null,

	@Json(name="humidity")
	val humidity: Int? = null,

	@Json(name="wind_speed")
	val windSpeed: Double? = null,
)

@JsonClass(generateAdapter = true)
data class WeatherItem(

	@Json(name="icon")
	val icon: String? = null,

	@Json(name="description")
	val description: String? = null,

	@Json(name="main")
	val main: String? = null,

	@Json(name="id")
	val id: Int? = null
)

@JsonClass(generateAdapter = true)
data class FeelsLike(

	@Json(name="eve")
	val eve: Double? = null,

	@Json(name="night")
	val night: Double? = null,

	@Json(name="day")
	val day: Double? = null,

	@Json(name="morn")
	val morn: Double? = null
)

@JsonClass(generateAdapter = true)
data class Current(
	@Json(name="dt")
	val dt: Long? = null,

	@Json(name="sunrise")
	val sunrise: Int? = null,

	@Json(name="temp")
	val temp: Double? = null,

	@Json(name="feels_like")
	val feelsLike: Double? = null,

	@Json(name="sunset")
	val sunset: Int? = null,

	@Json(name="weather")
	val weather: List<WeatherItem?>? = null,

	@Json(name="humidity")
	val humidity: Int? = null,

	@Json(name="wind_speed")
	val windSpeed: Double? = null
)
