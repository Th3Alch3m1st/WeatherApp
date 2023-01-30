package com.stocard.citysearch.data.dto

/**
 * Created by Rafiqul Hasan
 */
data class CityResponse(
	val id: Int,
	val cityName: String,
	val stateCode: String,
	val countryCode: String,
	val countryFullName: String,
	val lat: Double,
	val lon: Double
)