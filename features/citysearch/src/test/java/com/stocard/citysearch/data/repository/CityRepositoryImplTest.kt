package com.stocard.citysearch.data.repository

import com.stocard.citysearch.data.dto.CityResponse
import com.stocard.citysearch.data.local.ICitySourceLocal
import com.stocard.citysearch.testutils.getCities
import com.stocard.core.network.RequestException
import com.stocard.core.network.Resource
import com.stocard.testutil.returns
import com.stocard.testutil.shouldEqual
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Rafiqul Hasan
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CityRepositoryImplTest {
	companion object {
		const val NUMBER_OF_CITIES = 7
		const val INDEX_0_CITIES = "Avion"
		const val INDEX_6_CITIES = "Berlin Treptow"
		const val MSG_EXCEPTION = "Can't parse data"
	}

	@Mock
	lateinit var mockSourceLocal: ICitySourceLocal

	private lateinit var listCity: List<CityResponse>
	private lateinit var sutCityRepositoryImpl: CityRepositoryImpl


	@Before
	fun setup() {
		listCity = getCities()
		sutCityRepositoryImpl = CityRepositoryImpl(mockSourceLocal)
	}

	@Test
	fun `get Cities and it should return success`() {
		runBlocking {
			// Arrange
			success()

			// Act
			val response = sutCityRepositoryImpl.getCities()

			// Assert
			assertThat((response as Resource.Success)).isInstanceOf(Resource.Success::class.java)
		}
	}

	@Test
	fun `get Cities and it should return correct data`() {
		runBlocking {
			// Arrange
			success()

			// Act
			val response = sutCityRepositoryImpl.getCities()

			// Assert
			(response as Resource.Success<List<CityResponse>>).data.size shouldEqual NUMBER_OF_CITIES
			response.data[0].cityName shouldEqual INDEX_0_CITIES
			response.data[6].cityName shouldEqual INDEX_6_CITIES
		}
	}

	@Test
	fun `test get cities failure`() {
		runBlocking {
			// Arrange
			failure()

			// Act
			val response = sutCityRepositoryImpl.getCities()

			// Assert
			assertThat((response as Resource.Error).exception).isInstanceOf(RequestException::class.java)
			response.exception.message shouldEqual MSG_EXCEPTION
		}
	}

	private fun success() {
		runBlocking {
			mockSourceLocal.getCities() returns Resource.Success(listCity)
		}
	}

	private fun failure() {
		runBlocking {
			sutCityRepositoryImpl.getCities() returns Resource.Error(
				RequestException(MSG_EXCEPTION),
				0
			)
		}
	}


}