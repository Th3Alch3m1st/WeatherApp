package com.stocard.weatherinfo.di

import com.stocard.weatherinfo.data.location.ILocationTracker
import com.stocard.weatherinfo.data.location.LocationTrackerImpl
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
abstract class LocationTrackerModule {

	@Binds
	@Singleton
	abstract fun provideLocationTrackerImpl(tracker: LocationTrackerImpl): ILocationTracker
}