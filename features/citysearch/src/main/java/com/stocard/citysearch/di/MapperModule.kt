package com.stocard.citysearch.di

import com.stocard.citysearch.data.dto.CityResponse
import com.stocard.citysearch.mapper.CityResponseToCityUIModelMapper
import com.stocard.citysearch.domain.model.CityUIModel
import com.stocard.core.mapper.Mapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Rafiqul Hasan
 */


@Module
@InstallIn(SingletonComponent::class)
object  MapperModule {
	@Provides
	@Singleton
	fun provideMapper(): Mapper<CityResponse, CityUIModel> {
		return CityResponseToCityUIModelMapper()
	}
}