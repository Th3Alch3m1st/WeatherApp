package com.stocard.weatherinfo.domain.usecase

import com.stocard.core.model.UnitMeasurement
import com.stocard.core.network.RequestException
import com.stocard.core.network.Resource
import com.stocard.testutil.returns
import com.stocard.testutil.shouldEqual
import com.stocard.weatherinfo.data.api.WeatherInfoApiTest
import com.stocard.weatherinfo.data.remote.WeatherInfoRemoteSource
import com.stocard.weatherinfo.domain.model.WeatherInfoUIModel
import com.stocard.weatherinfo.mapper.WeatherInfoResponseToUIModelMapper
import com.stocard.weatherinfo.utils.TestUtils
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor

/**
 * Created by Rafiqul Hasan
 */
@RunWith(MockitoJUnitRunner::class)
class WeatherInfoUseCaseImplTest {
	companion object{
		const val WEATHER_INFO_HOURLY_LIST_SIZE = 48
		const val WEATHER_INFO_DAILY_LIST_SIZE = 5
	}
	@Mock
	lateinit var mockApi: WeatherInfoRemoteSource

	private lateinit var sutUseCase: WeatherInfoUseCaseImpl

	@Before
	fun setUp() {
		sutUseCase = WeatherInfoUseCaseImpl(mockApi, WeatherInfoResponseToUIModelMapper())
	}

	@Test
	fun `check argument pass correctly in getWeatherInfo fun`() {
		runBlocking {
			// Arrange
			successWeatherInfo()
			val acDouble = argumentCaptor<Double>()
			val acString = argumentCaptor<String>()

			// Act
			sutUseCase.getWeatherInfo(
				WeatherInfoApiTest.LATITUDE,
				WeatherInfoApiTest.LONGITUDE,
				UnitMeasurement.METRIC
			)
			Mockito.verify(mockApi)
				.getWeatherInfo(acDouble.capture(), acDouble.capture(), acString.capture())

			// Assert
			acDouble.allValues[0] shouldEqual WeatherInfoApiTest.LATITUDE
			acDouble.allValues[1] shouldEqual WeatherInfoApiTest.LONGITUDE
			acString.firstValue shouldEqual UnitMeasurement.METRIC.name.lowercase()
		}
	}

	@Test
	fun `get weather info data and it should return correct data`() {
		runBlocking {
			// Arrange
			successWeatherInfo()

			// Act
			val response = sutUseCase.getWeatherInfo(
				WeatherInfoApiTest.LATITUDE,
				WeatherInfoApiTest.LONGITUDE,
				UnitMeasurement.METRIC
			)
			// Assert
			(response as Resource.Success<WeatherInfoUIModel>).data.daily.size shouldEqual WEATHER_INFO_DAILY_LIST_SIZE
			response.data.hourly.size shouldEqual WEATHER_INFO_HOURLY_LIST_SIZE
			response.data.currentWeatherInfo.type shouldEqual  UnitMeasurement.METRIC
		}
	}

	@Test
	fun `test get weather on failure should return Error state with proper message`() {
		runBlocking {
			// Arrange
			failure()

			// Act
			val response = sutUseCase.getWeatherInfo(
				WeatherInfoApiTest.LATITUDE,
				WeatherInfoApiTest.LONGITUDE,
				UnitMeasurement.METRIC
			)

			// Assert
			assertThat((response as Resource.Error).exception).isInstanceOf(
				RequestException::class.java)
			response.exception.message shouldEqual WeatherInfoApiTest.ERROR_MSG
		}
	}

	private fun successWeatherInfo() {
		runBlocking {
			val testData = TestUtils.getWeatherInfoTestData("weather_info.json")
			mockApi.getWeatherInfo(any(), any(), any()) returns testData
		}
	}

	private fun failure() {
		runBlocking {
			mockApi.getWeatherInfo(any(), any(), any()) returns Resource.Error(RequestException(WeatherInfoApiTest.ERROR_MSG),0)
		}
	}
}