package com.stocard.citysearch.data.local

import com.stocard.citysearch.data.dto.CityResponse
import com.stocard.core.network.RequestException
import com.stocard.core.network.Resource
import javax.inject.Inject

/**
 * Created By Rafiqul Hasan
 */
class CitySourceLocalImpl @Inject constructor() : ICitySourceLocal {
	override suspend fun getCities(): Resource<List<CityResponse>> {
		val list = mutableListOf<CityResponse>()
		return try {
			//city_id,city_name,state_code,country_code,country_full,lat,lon
			val path = "res/raw/cities.csv"
			this.javaClass.classLoader?.getResourceAsStream(path)
				?.bufferedReader().use {
					it?.readLines()?.forEach { string ->
						val split = string.split(",")
						list.add(
							CityResponse(
								id = split.getOrNull(0)?.toIntOrNull() ?: 0,
								cityName = split.getOrNull(1) ?: "",
								stateCode = split.getOrNull(2) ?: "",
								countryCode = split.getOrNull(3) ?: "",
								countryFullName = split.getOrNull(4) ?: "",
								lat = split.getOrNull(split.size-2)?.toDouble() ?: 0.0,
								lon = split.getOrNull(split.size-1)?.toDouble() ?: 0.0
							)
						)
					}
				}
			Resource.Success(list)
		} catch (ex: Exception) {
			Resource.Error(RequestException(ex.message ?: "Parse Exception"), 0)
		}
	}
}