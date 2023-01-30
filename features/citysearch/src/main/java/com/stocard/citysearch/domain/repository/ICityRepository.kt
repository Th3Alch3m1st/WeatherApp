package com.stocard.citysearch.domain.repository

import com.stocard.citysearch.data.dto.CityResponse
import com.stocard.core.network.Resource

/**
 * Created By Rafiqul Hasan
 */
interface ICityRepository {
    suspend fun getCities(): Resource<List<CityResponse>>
}