package com.stocard.preference.di

import com.stocard.preference.usecase.ISettingPreference
import com.stocard.preference.usecase.SettingPreferenceImpl
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
abstract class SettingPreferenceModule {

	@Binds
	@Singleton
	abstract fun provideSettingPreference(sourceImpl: SettingPreferenceImpl): ISettingPreference
}