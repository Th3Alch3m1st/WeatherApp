package com.stocard.weatherinfo.mapper

import com.stocard.core.BuildConfig
import com.stocard.core.mapper.Mapper
import com.stocard.core.util.getDailyInfoEpochTimeToDateTime
import com.stocard.core.util.getHourlyInfoEpochTimeToDateTime
import com.stocard.weatherinfo.data.dto.WeatherInfoResponse
import com.stocard.weatherinfo.domain.model.*

/**
 * Created By Rafiqul Hasan
 */
class WeatherInfoResponseToUIModelMapper :
	Mapper<WeatherInfoResponse, WeatherInfoUIModel> {
	override fun map(input: WeatherInfoResponse): WeatherInfoUIModel {
		return WeatherInfoUIModel(
			currentWeatherInfo = CurrentWeatherInfo(
				location = input.timezone ?: "",
				time = getHourlyInfoEpochTimeToDateTime(input.current?.dt ?: 0),
				temp = input.current?.temp?.toFloat() ?: 0.0f,
				humidity = input.current?.humidity ?: 0,
				windSpeed = input.current?.windSpeed?.toFloat() ?: 0.0f,
				feelsLike = input.current?.feelsLike?.toFloat() ?: 0.0f,
				weatherIcon = WeatherInfoIcon(
					description = input.current?.weather?.getOrNull(0)?.description ?: "",
					icon = if (!input.current?.weather?.getOrNull(0)?.icon.isNullOrEmpty())
						"${BuildConfig.ICON_URL}/${input.current?.weather?.getOrNull(0)?.icon}@4x.png"
					else ""
				),
				type = input.unitMeasurement
			),
			hourly = input.hourly?.map { hourly ->
				WeatherInfoHourly(
					dateTime = getHourlyInfoEpochTimeToDateTime(hourly.dt ?: 0),
					temp = hourly.temp?.toFloat() ?: 0.0f,
					humidity = hourly.humidity ?: 0,
					weather = WeatherInfoIcon(
						description = hourly.weather?.getOrNull(0)?.description ?: "",
						icon = if (!hourly.weather?.getOrNull(0)?.icon.isNullOrEmpty())
							"${BuildConfig.ICON_URL}/${hourly.weather?.getOrNull(0)?.icon}@2x.png"
						else ""
					),
					type = input.unitMeasurement
				)
			} ?: mutableListOf(),
			daily = input.daily?.take(5)?.map { daily ->
				WeatherInfoDaily(
					dateTime = getDailyInfoEpochTimeToDateTime(daily.dt ?: 0),
					temp = Temperature(
						min = daily.temp?.min?.toFloat() ?: 0.0f,
						max = daily.temp?.max?.toFloat() ?: 0.0f,
						type =input.unitMeasurement
					),
					humidity = daily.humidity ?: 0,
					weather = WeatherInfoIcon(
						description = daily.weather?.getOrNull(0)?.description ?: "",
						icon = if (!daily.weather?.getOrNull(0)?.icon.isNullOrEmpty())
							"${BuildConfig.ICON_URL}/${daily.weather?.getOrNull(0)?.icon}@2x.png"
						else ""
					),
					type = input.unitMeasurement
				)
			} ?: mutableListOf()
		)
	}
}