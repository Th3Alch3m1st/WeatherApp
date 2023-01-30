package com.stocard.preference.usecase

import com.stocard.preference.datastore.PreferenceDataStoreConstants
import com.stocard.preference.datastore.domain.IPreferenceDataStoreAPI
import com.stocard.core.model.ThemeMode
import com.stocard.core.model.UnitMeasurement
import javax.inject.Inject

/**
 * Created By Rafiqul Hasan
 */

class SettingPreferenceImpl @Inject constructor(private val preference: IPreferenceDataStoreAPI) :
	ISettingPreference {
	override suspend fun getSelectedThemeMode(default: ThemeMode): ThemeMode {
		val value = preference.getFirstPreference(
			PreferenceDataStoreConstants.THEME_PREF_KEY,
			default.name
		)
		return try {
			ThemeMode.valueOf(value)
		} catch (ex: Exception) {
			ThemeMode.DEFAULT
		}
	}

	override suspend fun saveThemeModePreference(themeMode: ThemeMode) {
		preference.putPreference(PreferenceDataStoreConstants.THEME_PREF_KEY, themeMode.name)
	}

	override suspend fun getSelectedUnitMeasurement(default: UnitMeasurement): UnitMeasurement {
		val value = preference.getFirstPreference(
			PreferenceDataStoreConstants.UNIT_MEASUREMENT_PREF_KEY,
			default.name
		)
		return try {
			UnitMeasurement.valueOf(value)
		} catch (ex: Exception) {
			UnitMeasurement.METRIC
		}

	}

	override suspend fun saveUnitMeasurementPreference(unitMeasurement: UnitMeasurement) {
		preference.putPreference(
			PreferenceDataStoreConstants.UNIT_MEASUREMENT_PREF_KEY,
			unitMeasurement.name
		)
	}
}