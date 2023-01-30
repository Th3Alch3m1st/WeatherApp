package com.stocard.citysearch.di

import com.stocard.citysearch.data.local.CitySourceLocalImpl
import com.stocard.citysearch.data.local.ICitySourceLocal
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
abstract class CitySourceLocalModule {
	@Binds
	@Singleton
	abstract fun provideCitySourceLocal(toastRemoteSource: CitySourceLocalImpl): ICitySourceLocal
}