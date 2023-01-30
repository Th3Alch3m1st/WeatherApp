package com.stocard.weatherinfo.fake

import com.stocard.core.model.UnitMeasurement
import com.stocard.core.network.RequestException
import com.stocard.core.network.Resource
import com.stocard.weatherinfo.domain.model.*
import com.stocard.weatherinfo.domain.usecase.WeatherInfoUseCase
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rafiqul Hasan
 */
@Singleton
class FakeWeatherInfoUseCaseImpl @Inject constructor() : WeatherInfoUseCase {
	companion object {
		const val MSG_ERROR = "Invalid Token"
	}

	var isTestError = false
	override suspend fun getWeatherInfo(
		latitude: Double,
		longitude: Double,
		unitType: UnitMeasurement
	): Resource<WeatherInfoUIModel> {
		return if(isTestError){
			Resource.Error(RequestException(message = MSG_ERROR),0)
		}else{
			Resource.Success(getFakeWeatherInfo())
		}
	}

	private fun getFakeWeatherInfo(): WeatherInfoUIModel {
		return WeatherInfoUIModel(
			currentWeatherInfo = CurrentWeatherInfo(
				location = "Berlin",
				time = "12:00PM\n12/05",
				temp = 1.1f,
				humidity = 70,
				windSpeed = 12.0f,
				feelsLike = -1.2f,
				weatherIcon = WeatherInfoIcon(
					description = "Moderate Rain",
					icon = ""
				),
				type = UnitMeasurement.METRIC
			),
			hourly = listOf(
				WeatherInfoHourly(
					dateTime = "12:00PM\n12/05",
					temp = 23.58f,
					humidity = 81,
					weather = WeatherInfoIcon("Rainy 1", ""),
					type = UnitMeasurement.METRIC
				),
				WeatherInfoHourly(
					dateTime = "01:00PM\n12/05",
					temp = 24.58f,
					humidity = 82,
					weather = WeatherInfoIcon("Rainy 2", ""),
					type = UnitMeasurement.METRIC
				),
				WeatherInfoHourly(
					dateTime = "02:00PM\n12/05",
					temp = 25.58f,
					humidity = 83,
					weather = WeatherInfoIcon("Rainy 3", ""),
					type = UnitMeasurement.METRIC
				),
				WeatherInfoHourly(
					dateTime = "03:00PM\n12/05",
					temp = 25.58f,
					humidity = 84,
					weather = WeatherInfoIcon("Rainy 4", ""),
					type = UnitMeasurement.METRIC
				),
				WeatherInfoHourly(
					dateTime = "04:00PM\n12/05",
					temp = 26.58f,
					humidity = 85,
					weather = WeatherInfoIcon("Rainy 5", ""),
					type = UnitMeasurement.METRIC
				)

			),
			daily = listOf(
				WeatherInfoDaily(
					dateTime = "Sun, Dec 05",
					temp = Temperature(
						min = 22.2f,
						max = 28.2f,
						type = UnitMeasurement.METRIC
					),
					humidity = 80,
					weather = WeatherInfoIcon(
						description = "Moderate Rain",
						icon = ""
					),
					type = UnitMeasurement.METRIC
				), WeatherInfoDaily(
					dateTime = "Mon, Dec 06",
					temp = Temperature(
						min = 23.2f,
						max = 29.2f,
						type = UnitMeasurement.METRIC
					),
					humidity = 81,
					weather = WeatherInfoIcon(
						description = "Moderate Rain 1",
						icon = ""
					),
					type = UnitMeasurement.METRIC
				), WeatherInfoDaily(
					dateTime = "Tue, Dec 07",
					temp = Temperature(
						min = 24.2f,
						max = 30.2f,
						type = UnitMeasurement.METRIC
					),
					humidity = 82,
					weather = WeatherInfoIcon(
						description = "Moderate Rain 2",
						icon = ""
					),
					type = UnitMeasurement.METRIC
				), WeatherInfoDaily(
					dateTime = "Wed, Dec 08",
					temp = Temperature(
						min = 25.2f,
						max = 31.2f,
						type = UnitMeasurement.METRIC
					),
					humidity = 82,
					weather = WeatherInfoIcon(
						description = "Moderate Rain 3",
						icon = ""
					),
					type = UnitMeasurement.METRIC
				), WeatherInfoDaily(
					dateTime = "Thu, Dec 09",
					temp = Temperature(
						min = 25.2f,
						max = 31.2f,
						type = UnitMeasurement.METRIC
					),
					humidity = 82,
					weather = WeatherInfoIcon(
						description = "Moderate Rain 4",
						icon = ""
					),
					type = UnitMeasurement.METRIC
				)
			)
		)
	}

}