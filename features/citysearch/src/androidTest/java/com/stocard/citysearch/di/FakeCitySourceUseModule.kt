package com.stocard.citysearch.di

import com.stocard.citysearch.domain.usecase.ICityUseCase
import com.stocard.citysearch.fakeusecase.FakeCityUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

/**
 * Created by Rafiqul Hasan
 */
@Module
@TestInstallIn(
	components = [SingletonComponent::class],
	replaces = [CitySourceUseCaseModule::class]
)
abstract class FakeCitySourceUseModule {
	@Singleton
	@Binds
	abstract fun provideRepository(impl: FakeCityUseCaseImpl): ICityUseCase
}