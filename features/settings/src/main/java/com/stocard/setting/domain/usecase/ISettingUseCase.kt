package com.stocard.setting.domain.usecase

import com.stocard.setting.domain.model.ThemeModel
import com.stocard.setting.domain.model.UnitMeasurementModel

/**
 * Created By Rafiqul Hasan
 */
interface ISettingUseCase {
	fun getAvailableThemeMode(): List<ThemeModel>
	fun getAvailableUnit(): List<UnitMeasurementModel>
}