package com.stocard.weatherinfo.data.api

import com.stocard.testutil.shouldEqual
import com.stocard.weatherinfo.utils.TestUtils
import com.stocard.weatherinfo.utils.TestUtils.getOkHttpClient
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
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

@RunWith(JUnit4::class)
class WeatherInfoApiTest {
	companion object {
		const val LATITUDE = 52.5586
		const val LONGITUDE = 13.5739
		const val TIME_ZONE = "Europe/Berlin"
		const val UNIT_TYPE = "metic"
		const val ERROR_MSG = "Invalid authentication credentials"
		const val LOCATION_NAME = "Berlin"
	}

	@get:Rule
	val mockWebServer = MockWebServer()

	private lateinit var queryMap: Map<String, String>
	private lateinit var sutWeatherInfoApi: WeatherInfoApi

	@Before
	fun setUp() {
		val moshi = Moshi.Builder()
			.build()

		queryMap = mapOf(
			"lat" to "$LATITUDE",
			"lon" to "$LONGITUDE",
			"exclude" to "minutely",
			"unit" to UNIT_TYPE,
			"appid" to "78a07164952e030a671b9350f6"
		)

		sutWeatherInfoApi = Retrofit.Builder()
			.baseUrl(mockWebServer.url("/"))
			.addConverterFactory(MoshiConverterFactory.create(moshi))
			.client(getOkHttpClient())
			.build()
			.create(WeatherInfoApi::class.java)
	}

	@After
	fun shutDown() {
		mockWebServer.shutdown()
	}

	@Test
	fun `get weather info and it should return correct response data`(){
		runBlocking {

			// Act
			mockWebServer.enqueue(TestUtils.mockResponse("weather_info.json"))
			val response = sutWeatherInfoApi.getWeatherInfo(queryMap)

			// Assert
			response.body()?.lat shouldEqual LATITUDE
			response.body()?.lon shouldEqual LONGITUDE
			response.body()?.timezone shouldEqual TIME_ZONE
		}
	}
}