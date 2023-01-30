package com.stocard.weatherinfo.data.remote

import com.stocard.core.network.Resource
import com.stocard.testutil.shouldEqual
import com.stocard.weatherinfo.data.api.WeatherInfoApi
import com.stocard.weatherinfo.data.api.WeatherInfoApiTest
import com.stocard.weatherinfo.utils.TestUtils
import com.stocard.weatherinfo.utils.TestUtils.getOkHttpClient
import com.squareup.moshi.Moshi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Rafiqul Hasan
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class WeatherInfoRemoteSourceImplTest {
	@get:Rule
	val mockWebServer = MockWebServer()

	private lateinit var queryMap: Map<String, String>
	private lateinit var api: WeatherInfoApi
	private lateinit var sutWeatherInfoRemoteSourceImpl: WeatherInfoRemoteSourceImpl

	@Before
	fun setUp() {
		val moshi = Moshi.Builder()
			.build()

		queryMap = mapOf(
			"lat" to "${WeatherInfoApiTest.LATITUDE}",
			"lon" to "${WeatherInfoApiTest.LONGITUDE}",
			"exclude" to "minutely,current",
			"appid" to "78a07164952e030a671b9350f6"
		)

		api = Retrofit.Builder()
			.baseUrl(mockWebServer.url("/"))
			.addConverterFactory(MoshiConverterFactory.create(moshi))
			.client(getOkHttpClient())
			.build()
			.create(WeatherInfoApi::class.java)

		sutWeatherInfoRemoteSourceImpl = WeatherInfoRemoteSourceImpl(api, UnconfinedTestDispatcher())
	}

	@After
	fun shutDown() {
		mockWebServer.shutdown()
	}

	@Test
	fun `get weather info and it should return correct data`() {
		runBlocking {
			// Arrange
			mockWebServer.enqueue(TestUtils.mockResponse("weather_info.json"))

			// Act
			val response = sutWeatherInfoRemoteSourceImpl.getWeatherInfo(
				WeatherInfoApiTest.LATITUDE,
				WeatherInfoApiTest.LONGITUDE,
				WeatherInfoApiTest.UNIT_TYPE
			)

			// Assert

			(response as Resource.Success).data.lat shouldEqual WeatherInfoApiTest.LATITUDE
			response.data.lon shouldEqual WeatherInfoApiTest.LONGITUDE
			response.data.timezone shouldEqual WeatherInfoApiTest.TIME_ZONE
		}
	}
}