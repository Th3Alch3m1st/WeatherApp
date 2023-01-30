package com.stocard.weatherinfo.di

import com.stocard.weatherinfo.data.remote.WeatherInfoRemoteSource
import com.stocard.weatherinfo.data.remote.WeatherInfoRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created By Rafiqul Hasan
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherInfoRemoteSourceModule {
	@Binds
	@Singleton
	abstract fun provideWeatherInfoRemoteSource(sourceImpl: WeatherInfoRemoteSourceImpl): WeatherInfoRemoteSource
}