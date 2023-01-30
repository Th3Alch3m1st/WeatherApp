package com.stocard.setting.presentation

import com.stocard.core.model.ThemeMode
import com.stocard.core.model.UnitMeasurement
import com.stocard.preference.usecase.ISettingPreference
import com.stocard.setting.data.SettingRepositoryImpl
import com.stocard.setting.domain.usecase.ISettingUseCase
import com.stocard.testutil.returns
import com.stocard.testutil.shouldEqual
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argumentCaptor

/**
 * Created by Rafiqul Hasan
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class SettingViewModelTest {
	companion object{
		const val THEME_DATA_SIZE = 3
		const val UNIT_MEASUREMENT_SIZE = 2
	}
	@get:Rule
	val dispatcherRule = com.stocard.testutil.MainDispatcherRule()

	@Mock
	lateinit var mockUseCase: ISettingUseCase

	@Mock
	lateinit var mockPreference: ISettingPreference

	private lateinit var sutSettingViewModel: SettingsViewModel

	@Before
	fun setUp() {
		sutSettingViewModel = SettingsViewModel(mockUseCase, mockPreference)
	}

	@Test
	fun `state is initially loading`() = runTest {
		sutSettingViewModel.selectedTheme.value shouldEqual ThemeMode.DEFAULT
		sutSettingViewModel.selectedUnit.value shouldEqual UnitMeasurement.METRIC
	}

	@Test
	fun `get Theme Mode Data should return correct list`() = runTest {
		//Arrange
		successThemeData()

		//act
		val response = sutSettingViewModel.getThemeModeData()

		//Assert
		response.size shouldEqual THEME_DATA_SIZE
		response[0].themeMode shouldEqual ThemeMode.DEFAULT
		response[1].themeMode shouldEqual ThemeMode.LIGHT
		response[2].themeMode shouldEqual ThemeMode.DARK
	}

	@Test
	fun `get Unit Measurement Data should return correct list`() = runTest {
		//Arrange
		successUnitMeasurementData()

		//act
		val response = sutSettingViewModel.getUnitMeasurementData()

		//Assert
		response.size shouldEqual UNIT_MEASUREMENT_SIZE
		response[0].unit shouldEqual UnitMeasurement.METRIC
		response[1].unit shouldEqual UnitMeasurement.IMPERIAL
	}

	@Test
	fun `get Selected ThemeMode should return correct ThemeData`() = runTest {
		//Arrange
		successSaveThemeData()

		//act
		sutSettingViewModel.getSelectedTheme()

		//Assert
		val response = sutSettingViewModel.selectedTheme.first()
		response shouldEqual ThemeMode.DARK

	}

	@Test
	fun `get Selected Unit Measurement should return correct Measurement Unit`() = runTest {
		//Arrange
		successSaveUnitMeasurementData()

		//act
		sutSettingViewModel.getSelectedUnitMeasurement()

		//Assert
		val response = sutSettingViewModel.selectedUnit.first()
		response shouldEqual UnitMeasurement.IMPERIAL
	}

	@Test
	fun `check argument pass correctly in setSelectedThemeMode fun`() = runTest {
		//arrange
		val acType = argumentCaptor<ThemeMode>()
		successSaveThemeData()

		//act
		sutSettingViewModel.setSelectedThemeMode(ThemeMode.DARK)
		Mockito.verify(mockPreference).saveThemeModePreference(acType.capture())

		//Assert
		acType.firstValue shouldEqual ThemeMode.DARK
	}

	@Test
	fun `check argument pass correctly in setSelectedUnitMeasurement fun`() = runTest {
		//arrange
		val acType = argumentCaptor<UnitMeasurement>()
		successSaveUnitMeasurementData()

		//act
		sutSettingViewModel.setSelectedUnitMeasurement(UnitMeasurement.IMPERIAL)
		Mockito.verify(mockPreference).saveUnitMeasurementPreference(acType.capture())

		//Assert
		acType.firstValue shouldEqual UnitMeasurement.IMPERIAL
	}



	private fun successThemeData() {
		runBlocking {
			mockUseCase.getAvailableThemeMode() returns SettingRepositoryImpl().getAvailableThemeMode()
		}
	}

	private fun successSaveThemeData() {
		runBlocking {
			mockPreference.getSelectedThemeMode(ThemeMode.DEFAULT) returns ThemeMode.DARK
		}
	}

	private fun successUnitMeasurementData() {
		runBlocking {
			mockUseCase.getAvailableUnit() returns SettingRepositoryImpl().getAvailableUnit()
		}
	}

	private fun successSaveUnitMeasurementData() {
		runBlocking {
			mockPreference.getSelectedUnitMeasurement(UnitMeasurement.METRIC) returns UnitMeasurement.IMPERIAL
		}
	}
}