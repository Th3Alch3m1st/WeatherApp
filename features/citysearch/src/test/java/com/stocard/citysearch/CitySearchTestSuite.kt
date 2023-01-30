package com.stocard.citysearch

import com.stocard.citysearch.data.repository.CityRepositoryImplTest
import com.stocard.citysearch.domain.usecase.CityUseCaseImplTest
import com.stocard.citysearch.mapper.CityResponseToCityUIModelMapperTest
import com.stocard.citysearch.presentation.CitySearchViewModelTest
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
	CityRepositoryImplTest::class,
	CityUseCaseImplTest::class,
	CityResponseToCityUIModelMapperTest::class,
	CitySearchViewModelTest::class
)
class CitySearchTestSuite