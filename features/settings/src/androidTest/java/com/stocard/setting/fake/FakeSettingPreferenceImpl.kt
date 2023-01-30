package com.stocard.setting.fake

import com.stocard.core.model.ThemeMode
import com.stocard.core.model.UnitMeasurement
import com.stocard.preference.usecase.ISettingPreference
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rafiqul Hasan
 */
@Singleton
class FakeSettingPreferenceImpl @Inject constructor() : ISettingPreference {
	override suspend fun getSelectedThemeMode(default: ThemeMode): ThemeMode = ThemeMode.DARK

	override suspend fun saveThemeModePreference(themeMode: ThemeMode) {

	}

	override suspend fun getSelectedUnitMeasurement(default: UnitMeasurement) =
		UnitMeasurement.IMPERIAL

	override suspend fun saveUnitMeasurementPreference(unitMeasurement: UnitMeasurement) {

	}
}