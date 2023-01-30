package com.stocard.setting.di

import com.stocard.setting.data.SettingRepositoryImpl
import com.stocard.setting.domain.repository.ISettingRepository
import com.stocard.setting.domain.usecase.ISettingUseCase
import com.stocard.setting.domain.usecase.SettingUseCaseImpl
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
abstract class SettingRepositoryModule {

	@Binds
	@Singleton
	abstract fun provideSettingUseCaseImpl(useCaseImpl: SettingRepositoryImpl): ISettingRepository
}