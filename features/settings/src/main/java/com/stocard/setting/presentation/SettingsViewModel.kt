package com.stocard.setting.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocard.core.model.ThemeMode
import com.stocard.core.model.UnitMeasurement
import com.stocard.preference.usecase.ISettingPreference
import com.stocard.setting.domain.usecase.ISettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created By Rafiqul Hasan
 */

@HiltViewModel
class SettingsViewModel @Inject constructor(
	private val useCase: ISettingUseCase,
	private val settingPreference: ISettingPreference
) : ViewModel() {

	private val _selectedTheme = MutableStateFlow(ThemeMode.DEFAULT)
	val selectedTheme = _selectedTheme.asStateFlow()

	private val _selectedUnit = MutableStateFlow(UnitMeasurement.METRIC)
	val selectedUnit = _selectedUnit.asStateFlow()

	fun getThemeModeData() = useCase.getAvailableThemeMode()

	fun getUnitMeasurementData() = useCase.getAvailableUnit()

	fun getSelectedTheme() {
		viewModelScope.launch {
			_selectedTheme.emit(settingPreference.getSelectedThemeMode(ThemeMode.DEFAULT))
		}
	}

	fun setSelectedThemeMode(themeMode: ThemeMode) {
		viewModelScope.launch {
			settingPreference.saveThemeModePreference(themeMode)
		}
	}

	fun getSelectedUnitMeasurement() {
		viewModelScope.launch {
			_selectedUnit.emit(settingPreference.getSelectedUnitMeasurement(UnitMeasurement.METRIC))
		}
	}

	fun setSelectedUnitMeasurement(unitMeasurement: UnitMeasurement) {
		viewModelScope.launch {
			settingPreference.saveUnitMeasurementPreference(unitMeasurement)
		}
	}
}