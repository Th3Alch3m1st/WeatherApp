package com.stocard.weatherinfo.presentation

import com.stocard.core.model.UnitMeasurement
import com.stocard.core.network.RequestException
import com.stocard.core.network.Resource
import com.stocard.preference.usecase.ISettingPreference
import com.stocard.testutil.returns
import com.stocard.testutil.shouldEqual
import com.stocard.weatherinfo.data.api.WeatherInfoApiTest.Companion.ERROR_MSG
import com.stocard.weatherinfo.data.api.WeatherInfoApiTest.Companion.LATITUDE
import com.stocard.weatherinfo.data.api.WeatherInfoApiTest.Companion.LOCATION_NAME
import com.stocard.weatherinfo.data.api.WeatherInfoApiTest.Companion.LONGITUDE
import com.stocard.weatherinfo.data.location.ILocationTracker
import com.stocard.weatherinfo.domain.model.LocationInfo
import com.stocard.weatherinfo.domain.model.WeatherInfoUIModel
import com.stocard.weatherinfo.domain.usecase.WeatherInfoUseCase
import com.stocard.weatherinfo.domain.usecase.WeatherInfoUseCaseImplTest.Companion.WEATHER_INFO_DAILY_LIST_SIZE
import com.stocard.weatherinfo.domain.usecase.WeatherInfoUseCaseImplTest.Companion.WEATHER_INFO_HOURLY_LIST_SIZE
import com.stocard.weatherinfo.mapper.WeatherInfoResponseToUIModelMapper
import com.stocard.weatherinfo.utils.TestUtils
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
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor

/**
 * Created by Rafiqul Hasan
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class WeatherInfoViewModelTest {
	@get:Rule
	val dispatcherRule = com.stocard.testutil.MainDispatcherRule()

	@Mock
	lateinit var mockUserCase: WeatherInfoUseCase

	@Mock
	lateinit var mockLocationTracker: ILocationTracker

	@Mock
	lateinit var mockSettingPreference: ISettingPreference

	private lateinit var sutViewModel: WeatherInfoViewModel

	//data set
	private lateinit var responseWeatherInfo: WeatherInfoUIModel

	@Before
	fun setUp() {
		sutViewModel = WeatherInfoViewModel(
			userCase = mockUserCase,
			locationTracker = mockLocationTracker,
			settingPreference = mockSettingPreference
		)

		val mapper = WeatherInfoResponseToUIModelMapper()
		responseWeatherInfo =
			mapper.map(
				(TestUtils.getWeatherInfoTestData("weather_info.json") as Resource.Success).data.apply {
					unitMeasurement = UnitMeasurement.METRIC
				}
			)
	}

	@Test
	fun `state is initially loading`() = runTest {
		sutViewModel.weatherInfoStateFlow.value shouldEqual Resource.Loading
	}

	@Test
	fun `check argument pass correctly in getWeatherInfo fun`() = runTest {
		// Arrange
		successWeatherInfo()
		val acDouble = argumentCaptor<Double>()
		val acType = argumentCaptor<UnitMeasurement>()

		// Act
		sutViewModel.getWeatherInfo(LATITUDE, LONGITUDE)
		verify(mockUserCase)
			.getWeatherInfo(acDouble.capture(), acDouble.capture(), acType.capture())

		// Assert
		acDouble.allValues[0] shouldEqual LATITUDE
		acDouble.allValues[1] shouldEqual LONGITUDE
		acType.firstValue shouldEqual UnitMeasurement.METRIC
	}

	@Test
	fun `get weather info data and it should return correct data`() = runTest {
		// Arrange
		successWeatherInfo()

		// Act
		sutViewModel.getWeatherInfo(LATITUDE, LONGITUDE)

		// Assert
		val response = sutViewModel.weatherInfoStateFlow.first()
		(response as Resource.Success<WeatherInfoUIModel>).data.daily.size shouldEqual WEATHER_INFO_DAILY_LIST_SIZE
		response.data.hourly.size shouldEqual WEATHER_INFO_HOURLY_LIST_SIZE
		response.data.currentWeatherInfo.type shouldEqual UnitMeasurement.METRIC
	}

	@Test
	fun `test get weather info failure`() = runTest {
		// Arrange
		failure()

		// Act
		sutViewModel.getWeatherInfo(LATITUDE, LONGITUDE)

		// Assert
		val response = sutViewModel.weatherInfoStateFlow.first()
		assertThat((response as Resource.Error).exception).isInstanceOf(RequestException::class.java)
		response.exception.message shouldEqual ERROR_MSG
	}

	@Test
	fun `get location should return correct location info`() = runTest {
		//Arrange
		locationSuccess()

		//Act
		sutViewModel.getLocation()

		//Assert
		val response = sutViewModel.locationFlow.first()
		response?.lat shouldEqual LATITUDE
		response?.lon shouldEqual LONGITUDE
		response?.name shouldEqual LOCATION_NAME
	}

	@Test
	fun `if get location fail should return null`() = runTest {
		//Arrange
		locationFail()

		//Act
		sutViewModel.getLocation()

		//Assert
		val response = sutViewModel.locationFlow.first()
		response shouldEqual null
	}

	private fun successWeatherInfo() {
		runBlocking {
			mockSettingPreference.getSelectedUnitMeasurement(any()) returns UnitMeasurement.METRIC

			mockUserCase.getWeatherInfo(any(), any(), any()) returns Resource.Success(
				responseWeatherInfo
			)
		}
	}

	private fun failure() {
		runBlocking {
			mockSettingPreference.getSelectedUnitMeasurement(any()) returns UnitMeasurement.METRIC

			mockUserCase.getWeatherInfo(
				any(),
				any(),
				any()
			) returns Resource.Error(RequestException(ERROR_MSG), 0)
		}
	}

	private fun locationSuccess() {
		runBlocking {
			mockLocationTracker.getCurrentLocation() returns LocationInfo(
				LATITUDE,
				LONGITUDE,
				LOCATION_NAME
			)
		}
	}

	private fun locationFail() {
		runBlocking {
			mockLocationTracker.getCurrentLocation() returns null
		}
	}

}