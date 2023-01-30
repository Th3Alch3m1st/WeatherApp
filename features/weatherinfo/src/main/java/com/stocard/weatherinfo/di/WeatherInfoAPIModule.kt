package com.stocard.weatherinfo.di

import com.stocard.core.mapper.Mapper
import com.stocard.weatherinfo.data.api.WeatherInfoApi
import com.stocard.weatherinfo.data.dto.WeatherInfoResponse
import com.stocard.weatherinfo.domain.model.WeatherInfoUIModel
import com.stocard.weatherinfo.mapper.WeatherInfoResponseToUIModelMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created By Rafiqul Hasan
 */
@Module
@InstallIn(SingletonComponent::class)
object  WeatherInfoAPIModule {
	@Provides
	@Singleton
	fun provideWeatherInfoService(retrofit: Retrofit): WeatherInfoApi {
		return retrofit.create(WeatherInfoApi::class.java)
	}
	@Provides
	@Singleton
	fun provideMapper(): Mapper<WeatherInfoResponse, WeatherInfoUIModel> {
		return WeatherInfoResponseToUIModelMapper()
	}
}