package com.stocard.citysearch.data.local

import com.stocard.citysearch.data.dto.CityResponse
import com.stocard.core.network.Resource

/**
 * Created By Rafiqul Hasan
 */
interface ICitySourceLocal {
    suspend fun getCities(): Resource<List<CityResponse>>
}