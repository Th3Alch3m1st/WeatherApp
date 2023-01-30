package com.stocard.weatherinfo.di

import com.stocard.weatherinfo.domain.usecase.WeatherInfoUseCase
import com.stocard.weatherinfo.domain.usecase.WeatherInfoUseCaseImpl
import com.stocard.weatherinfo.fake.FakeWeatherInfoUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

/**
 * Created By Rafiqul Hasan
 */
@Module
@TestInstallIn(
	components = [SingletonComponent::class],
	replaces = [WeatherInfoUseCaseModule::class]
)
abstract class FakeWeatherInfoUseCaseModule {

	@Binds
	@Singleton
	abstract fun provideWeatherInfoRemoteImpl(useCaseImpl: FakeWeatherInfoUseCaseImpl): WeatherInfoUseCase
}