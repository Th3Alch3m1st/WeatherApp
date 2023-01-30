package com.stocard.citysearch.domain.usecase

import com.stocard.citysearch.domain.model.CityUIModel
import com.stocard.core.network.Resource

/**
 * Created By Rafiqul Hasan
 */
interface ICityUseCase {
    suspend fun getCities(): Resource<List<CityUIModel>>
}