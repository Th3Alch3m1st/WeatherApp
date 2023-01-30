package com.stocard.citysearch.data.repository

import com.stocard.citysearch.data.dto.CityResponse
import com.stocard.citysearch.data.local.ICitySourceLocal
import com.stocard.citysearch.domain.repository.ICityRepository
import com.stocard.core.network.Resource
import javax.inject.Inject

/**
 * Created By Rafiqul Hasan
 * city_id,city_name,state_code,country_code,country_full,lat,lon
 */
class CityRepositoryImpl @Inject constructor(private val citySourceLocal: ICitySourceLocal) :
	ICityRepository {
	override suspend fun getCities(): Resource<List<CityResponse>> {
		return citySourceLocal.getCities()
	}
}