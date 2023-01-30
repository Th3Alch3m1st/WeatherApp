package com.stocard.citysearch.mapper

import com.stocard.citysearch.data.dto.CityResponse
import com.stocard.citysearch.domain.model.CityUIModel
import com.stocard.core.mapper.Mapper

/**
 * Created by Rafiqul Hasan
 */
class CityResponseToCityUIModelMapper : Mapper<CityResponse, CityUIModel> {
	override fun map(input: CityResponse): CityUIModel {
		return CityUIModel(
			cityName = input.cityName,
			country = input.countryFullName,
			lat = input.lat,
			lon = input.lon
		)
	}
}