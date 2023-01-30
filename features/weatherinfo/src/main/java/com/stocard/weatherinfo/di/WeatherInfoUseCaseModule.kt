package com.stocard.weatherinfo.di

import com.stocard.weatherinfo.domain.usecase.WeatherInfoUseCase
import com.stocard.weatherinfo.domain.usecase.WeatherInfoUseCaseImpl
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
abstract class WeatherInfoUseCaseModule {

	@Binds
	@Singleton
	abstract fun provideWeatherInfoRemoteImpl(useCaseImpl: WeatherInfoUseCaseImpl): WeatherInfoUseCase
}