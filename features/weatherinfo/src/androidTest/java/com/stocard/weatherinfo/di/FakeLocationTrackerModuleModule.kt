package com.stocard.weatherinfo.di

import com.stocard.weatherinfo.data.location.ILocationTracker
import com.stocard.weatherinfo.fake.FakeLocationTrackerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

/**
 * Created By Rafiqul Hasan
 */
@Module
@TestInstallIn(
	components = [SingletonComponent::class],
	replaces = [LocationTrackerModule::class]
)
abstract class FakeLocationTrackerModuleModule {

	@Binds
	@Singleton
	abstract fun provideLocationTrackerImpl(tracker: FakeLocationTrackerImpl): ILocationTracker
}