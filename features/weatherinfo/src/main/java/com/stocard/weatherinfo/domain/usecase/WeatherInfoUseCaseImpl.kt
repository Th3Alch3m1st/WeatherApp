package com.stocard.weatherinfo.domain.usecase

import com.stocard.core.mapper.Mapper
import com.stocard.core.network.Resource
import com.stocard.core.model.UnitMeasurement
import com.stocard.weatherinfo.data.dto.WeatherInfoResponse
import com.stocard.weatherinfo.data.remote.WeatherInfoRemoteSource
import com.stocard.weatherinfo.domain.model.WeatherInfoUIModel
import javax.inject.Inject

/**
 * Created By Rafiqul Hasan
 */
class WeatherInfoUseCaseImpl @Inject constructor(
	private val remoteSource: WeatherInfoRemoteSource,
	private val mapper: Mapper<WeatherInfoResponse, WeatherInfoUIModel>
):WeatherInfoUseCase {
	override suspend fun getWeatherInfo(
		latitude: Double,
		longitude: Double,
		unitType: UnitMeasurement
	): Resource<WeatherInfoUIModel> {
		return when(val response = remoteSource.getWeatherInfo(latitude, longitude, unitType.name.lowercase())){
			is Resource.Success->{
				Resource.Success(mapper.map(response.data.apply {
					unitMeasurement = unitType
				}))
			}
			else->{
				val errorResponse = response as Resource.Error
				Resource.Error(errorResponse.exception,errorResponse.code)
			}
		}

	}

}