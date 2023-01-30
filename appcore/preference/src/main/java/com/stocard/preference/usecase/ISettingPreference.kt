package com.stocard.preference.usecase

import com.stocard.core.model.ThemeMode
import com.stocard.core.model.UnitMeasurement

/**
 * Created By Rafiqul Hasan
 */
interface ISettingPreference {
	suspend fun getSelectedThemeMode(default: ThemeMode): ThemeMode
	suspend fun saveThemeModePreference(themeMode: ThemeMode)
	suspend fun getSelectedUnitMeasurement(default: UnitMeasurement): UnitMeasurement
	suspend fun saveUnitMeasurementPreference(unitMeasurement: UnitMeasurement)
}