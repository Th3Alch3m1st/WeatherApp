package com.stocard.citysearch.presentation

import com.stocard.citysearch.data.repository.CityRepositoryImplTest.Companion.INDEX_0_CITIES
import com.stocard.citysearch.data.repository.CityRepositoryImplTest.Companion.INDEX_6_CITIES
import com.stocard.citysearch.data.repository.CityRepositoryImplTest.Companion.MSG_EXCEPTION
import com.stocard.citysearch.data.repository.CityRepositoryImplTest.Companion.NUMBER_OF_CITIES
import com.stocard.citysearch.domain.model.CityUIModel
import com.stocard.citysearch.domain.usecase.ICityUseCase
import com.stocard.citysearch.testutils.getCityUIModel
import com.stocard.core.network.RequestException
import com.stocard.core.network.Resource
import com.stocard.testutil.returns
import com.stocard.testutil.shouldEqual
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Rafiqul Hasan
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CitySearchViewModelTest {

	@get:Rule
	val dispatcherRule = com.stocard.testutil.MainDispatcherRule()

	@Mock
	lateinit var mockICityUseCase: ICityUseCase

	private lateinit var sutCitySearchViewModel: CitySearchViewModel

	private lateinit var list: List<CityUIModel>

	@Before
	fun setUp() {
		sutCitySearchViewModel = CitySearchViewModel(mockICityUseCase)
		list = getCityUIModel()
	}

	@Test
	fun `state is initially loading`() = runTest {
		sutCitySearchViewModel.cities.value shouldEqual Resource.Loading
	}

	@Test
	fun `get Cities and it should return correct data`() = runTest {
		// Arrange
		success()

		// Act
		sutCitySearchViewModel.getCities()

		// Assert
		val response = sutCitySearchViewModel.cities.first()
		(response as Resource.Success<List<CityUIModel>>).data.size shouldEqual NUMBER_OF_CITIES
		response.data[0].cityName shouldEqual INDEX_0_CITIES
		response.data[6].cityName shouldEqual INDEX_6_CITIES
	}

	@Test
	fun `test get cities failure`() = runTest {
		//Arrange
		failure()

		//Act
		sutCitySearchViewModel.getCities()

		//Assert
		val response = sutCitySearchViewModel.cities.first()
		assertThat((response as Resource.Error).exception).isInstanceOf(RequestException::class.java)
		response.exception.message shouldEqual MSG_EXCEPTION
	}

	private fun success() {
		runBlocking {
			mockICityUseCase.getCities() returns Resource.Success(list)
		}
	}

	private fun failure() {
		runBlocking {
			mockICityUseCase.getCities() returns Resource.Error(RequestException(MSG_EXCEPTION), 0)
		}
	}
}