package com.stocard.setting.domain.usecase

import com.stocard.setting.domain.model.ThemeModel
import com.stocard.setting.domain.model.UnitMeasurementModel
import com.stocard.setting.domain.repository.ISettingRepository
import javax.inject.Inject

/**
 * Created By Rafiqul Hasan
 */
class SettingUseCaseImpl @Inject constructor(private val repository: ISettingRepository) :
	ISettingUseCase {
	override fun getAvailableThemeMode(): List<ThemeModel> {
		return repository.getAvailableThemeMode()
	}

	override fun getAvailableUnit(): List<UnitMeasurementModel> {
		return repository.getAvailableUnit()
	}
}