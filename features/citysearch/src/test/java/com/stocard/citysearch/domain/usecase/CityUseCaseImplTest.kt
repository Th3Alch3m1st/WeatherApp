package com.stocard.citysearch.domain.usecase

import com.stocard.citysearch.data.dto.CityResponse
import com.stocard.citysearch.data.repository.CityRepositoryImplTest.Companion.INDEX_0_CITIES
import com.stocard.citysearch.data.repository.CityRepositoryImplTest.Companion.INDEX_6_CITIES
import com.stocard.citysearch.data.repository.CityRepositoryImplTest.Companion.MSG_EXCEPTION
import com.stocard.citysearch.data.repository.CityRepositoryImplTest.Companion.NUMBER_OF_CITIES
import com.stocard.citysearch.domain.model.CityUIModel
import com.stocard.citysearch.domain.repository.ICityRepository
import com.stocard.citysearch.mapper.CityResponseToCityUIModelMapper
import com.stocard.citysearch.testutils.getCities
import com.stocard.core.network.RequestException
import com.stocard.core.network.Resource
import com.stocard.testutil.returns
import com.stocard.testutil.shouldEqual
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
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
class CityUseCaseImplTest {

	@Mock
	lateinit var mockCityRepository: ICityRepository

	private lateinit var listCity: List<CityResponse>

	private lateinit var sutCityUseCaseImpl: CityUseCaseImpl

	private val mapper = CityResponseToCityUIModelMapper()

	@Before
	fun setup() {
		listCity = getCities()
		sutCityUseCaseImpl = CityUseCaseImpl(mockCityRepository,mapper)
	}

	@Test
	fun `get Cities and it should return success`() {
		runBlocking {
			// Arrange
			success()

			// Act
			val response = sutCityUseCaseImpl.getCities()

			// Assert
			Assertions.assertThat((response as Resource.Success)).isInstanceOf(Resource.Success::class.java)
		}
	}

	@Test
	fun `get Cities and it should return correct data`() {
		runBlocking {
			// Arrange
			success()

			// Act
			val response = sutCityUseCaseImpl.getCities()

			// Assert
			(response as Resource.Success<List<CityUIModel>>).data.size shouldEqual NUMBER_OF_CITIES
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
			val response = sutCityUseCaseImpl.getCities()

			// Assert
			Assertions.assertThat((response as Resource.Error).exception).isInstanceOf(RequestException::class.java)
			response.exception.message shouldEqual MSG_EXCEPTION
		}
	}

	private fun success() {
		runBlocking {
			mockCityRepository.getCities() returns Resource.Success(listCity)
		}
	}

	private fun failure() {
		runBlocking {
			mockCityRepository.getCities() returns Resource.Error(
				RequestException(MSG_EXCEPTION),
				0
			)
		}
	}
}