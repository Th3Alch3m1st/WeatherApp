package com.stocard.citysearch.fakeusecase

import com.stocard.citysearch.domain.model.CityUIModel
import com.stocard.citysearch.domain.usecase.ICityUseCase
import com.stocard.core.network.RequestException
import com.stocard.core.network.Resource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rafiqul Hasan
 */
@Singleton
class FakeCityUseCaseImpl @Inject constructor() : ICityUseCase {
	companion object {
		const val MSG_NOT_CITY_FOUNT = "No City Found"
		const val MSG_ERROR = "Can't parse the city list"
		const val NUMBER_OF_ITEMS = 7
		const val TRIGGER_SEARCH = "Berlin"
		const val NUMBER_OF_ITEM_AFTER_SEARCH = 2
		const val TRIGGER_EMPTY_RESPONSE = "DHAKA"

	}

	var isTestError = false

	override suspend fun getCities(): Resource<List<CityUIModel>> {
		return if (isTestError) {
			Resource.Error(RequestException(MSG_ERROR), 0)
		} else {
			Resource.Success(list)
		}
	}

	private val list = listOf(
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
}