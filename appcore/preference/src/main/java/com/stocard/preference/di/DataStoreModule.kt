package com.stocard.preference.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created By Rafiqul Hasan
 */
@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

	private val Context.dataStore by preferencesDataStore(
		name = "PreferenceDataStore"
	)

	@Singleton
	@Provides
	fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
		return appContext.dataStore
	}
}