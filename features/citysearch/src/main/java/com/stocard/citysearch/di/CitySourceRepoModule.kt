package com.stocard.citysearch.di

import com.stocard.citysearch.data.repository.CityRepositoryImpl
import com.stocard.citysearch.domain.repository.ICityRepository
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
abstract class CitySourceRepoModule {
	@Binds
	@Singleton
	abstract fun provideCitySourceRepo(toastRemoteSource: CityRepositoryImpl): ICityRepository
}