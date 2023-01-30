package com.stocard.setting.data

import com.stocard.core.model.ThemeMode
import com.stocard.core.model.UnitMeasurement
import com.stocard.setting.domain.model.ThemeModel
import com.stocard.setting.domain.model.UnitMeasurementModel
import com.stocard.setting.domain.repository.ISettingRepository
import javax.inject.Inject

/**
 * Created By Rafiqul Hasan
 */
class SettingRepositoryImpl @Inject constructor() :
	ISettingRepository {
	override fun getAvailableThemeMode(): List<ThemeModel> {
		return listOf(
			ThemeModel("System", themeMode = ThemeMode.DEFAULT),
			ThemeModel("Light", themeMode = ThemeMode.LIGHT),
			ThemeModel("Dark", themeMode = ThemeMode.DARK),
		)
	}

	override fun getAvailableUnit(): List<UnitMeasurementModel> {
		return listOf(
			UnitMeasurementModel("Metric", unit = UnitMeasurement.METRIC),
			UnitMeasurementModel("Imperial", unit = UnitMeasurement.IMPERIAL)
		)
	}
}