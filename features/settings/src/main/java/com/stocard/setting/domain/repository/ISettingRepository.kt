package com.stocard.setting.domain.repository

import com.stocard.setting.domain.model.ThemeModel
import com.stocard.setting.domain.model.UnitMeasurementModel

/**
 * Created By Rafiqul Hasan
 */
interface ISettingRepository {
	fun getAvailableThemeMode(): List<ThemeModel>
	fun getAvailableUnit(): List<UnitMeasurementModel>
}