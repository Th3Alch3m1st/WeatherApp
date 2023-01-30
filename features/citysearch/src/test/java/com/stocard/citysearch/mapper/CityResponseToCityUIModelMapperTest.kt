package com.stocard.citysearch.mapper

import com.stocard.citysearch.data.dto.CityResponse
import com.stocard.testutil.shouldEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Rafiqul Hasan
 */
@RunWith(JUnit4::class)
class CityResponseToCityUIModelMapperTest {
	private lateinit var sutMapper: CityResponseToCityUIModelMapper
	private lateinit var cityResponse: CityResponse

	@Before
	fun setUp() {
		sutMapper = CityResponseToCityUIModelMapper()
		cityResponse = CityResponse(
			1, "Avion", "32", "FR", "France", 50.41038, 2.83053
		)
	}

	@Test
	fun `CityResponseToCityUIModelMapper class should convert CityResponse to CityUIModel class with correct data`() {
		//act
		val uiModel = sutMapper.map(cityResponse)

		//assert
		uiModel.cityName shouldEqual cityResponse.cityName
		uiModel.country shouldEqual cityResponse.countryFullName
		uiModel.lat shouldEqual cityResponse.lat
		uiModel.lon shouldEqual cityResponse.lon
	}
}