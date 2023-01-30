package com.stocard.citysearch.domain.usecase

import com.stocard.citysearch.data.dto.CityResponse
import com.stocard.citysearch.domain.model.CityUIModel
import com.stocard.citysearch.domain.repository.ICityRepository
import com.stocard.core.mapper.Mapper
import com.stocard.core.network.Resource
import javax.inject.Inject

/**
 * Created by Rafiqul Hasan
 */
class CityUseCaseImpl @Inject constructor(
	private val cityRepository: ICityRepository,
	private val mapper: Mapper<CityResponse, CityUIModel>
) : ICityUseCase {
	override suspend fun getCities(): Resource<List<CityUIModel>> {
		return when (val response = cityRepository.getCities()) {
			is Resource.Success -> {
				Resource.Success(response.data.map { mapper.map(it) })
			}
			else -> {
				val errorResponse = response as Resource.Error
				Resource.Error(errorResponse.exception, errorResponse.code)
			}

		}
	}
}