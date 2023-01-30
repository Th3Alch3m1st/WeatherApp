package com.stocard.weatherinfo

import com.stocard.weatherinfo.data.api.WeatherInfoApiTest
import com.stocard.weatherinfo.data.remote.WeatherInfoRemoteSourceImplTest
import com.stocard.weatherinfo.domain.usecase.WeatherInfoUseCaseImplTest
import com.stocard.weatherinfo.mapper.WeatherInfoResponseToUIModelMapperTest
import com.stocard.weatherinfo.presentation.WeatherInfoViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

/**
 * Created by Rafiqul Hasan
 */
@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@SuiteClasses(
	WeatherInfoApiTest::class,
	WeatherInfoRemoteSourceImplTest::class,
	WeatherInfoUseCaseImplTest::class,
	WeatherInfoResponseToUIModelMapperTest::class,
	WeatherInfoViewModelTest::class
)
class WeatherInfoTestSuite