package com.stocard.citysearch.di

import com.stocard.citysearch.domain.usecase.CityUseCaseImpl
import com.stocard.citysearch.domain.usecase.ICityUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Rafiqul Hasan
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class CitySourceUseCaseModule {
	@Binds
	@Singleton
	abstract fun provideCitySourceUseCase(toastRemoteSource: CityUseCaseImpl): ICityUseCase
}