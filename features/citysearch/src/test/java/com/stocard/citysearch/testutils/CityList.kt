package com.stocard.citysearch.testutils

import com.stocard.citysearch.data.dto.CityResponse
import com.stocard.citysearch.domain.model.CityUIModel

/**
 * Created by Rafiqul Hasan
 */


fun getCities() = listOf(
	CityResponse(
		1, "Avion", "32", "FR", "France", 50.41038, 2.83053
	),
	CityResponse(
		2, "Amiens", "32", "FR", "France", 49.9, 2.3
	),
	CityResponse(
		3, "Cachan", "11", "FR", "France", 48.41038, 2.6
	),
	CityResponse(
		4, "Valencia", "64", "FR", "France", 39.46975, -0.37739
	),
	CityResponse(
		5, "Valdepeñas", "53", "ES", "Spain", 38.76211, -3.38483
	),
	CityResponse(
		6, "Berlin", "16", "DE", "Germany", 52.52437, 13.41053
	),
	CityResponse(
		7, "Berlin Treptow", "16", "DE", "Germany", 52.49376, 13.44469
	),
)

fun getCityUIModel() = listOf(
	CityUIModel(
		"Avion", "France", 50.41038, 2.83053
	),
	CityUIModel(
		"Amiens", "France", 49.9, 2.3
	),
	CityUIModel(
		"Cachan", "France", 48.41038, 2.6
	),
	CityUIModel(
		"Valencia", "France", 39.46975, -0.37739
	),
	CityUIModel(
		"Valdepeñas", "Spain", 38.76211, -3.38483
	),
	CityUIModel(
		"Berlin", "Germany", 52.52437, 13.41053
	),
	CityUIModel(
		"Berlin Treptow", "Germany", 52.49376, 13.44469
	)
)