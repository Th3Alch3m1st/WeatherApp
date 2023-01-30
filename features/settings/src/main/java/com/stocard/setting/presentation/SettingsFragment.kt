package com.stocard.setting.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.stocard.core.fragment.BaseFragment
import com.stocard.core.util.setSafeOnClickListener
import com.stocard.core.model.ThemeMode
import com.stocard.setting.R
import com.stocard.setting.databinding.FragmentSettingBinding
import com.stocard.setting.presentation.dialog.UnitMeasurementDialog
import com.stocard.core.util.ThemeHelper
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created By Rafiqul Hasan
 */

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingBinding>() {
	private val viewModel: SettingsViewModel by viewModels()

	override val layoutResourceId: Int
		get() = R.layout.fragment_setting

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initToolbar()
		initView()
		initClickListener()
		viewModel.getSelectedTheme()
		viewModel.getSelectedUnitMeasurement()
	}

	private fun initToolbar() {
		fragmentCommunicator?.setActionBar(dataBinding.layoutToolbar.toolbar, true)
	}

	private fun initView() {
		lifecycleScope.launchWhenStarted {
			viewModel.selectedTheme.collect { theme ->
				val themeMode = viewModel.getThemeModeData().find { it.themeMode == theme }
				dataBinding.llTheme.tvSettingName.text = getString(R.string.title_theme)
				dataBinding.llTheme.tvSelectedSetting.text = themeMode?.name ?: ""
			}
		}

		lifecycleScope.launchWhenStarted {
			viewModel.selectedUnit.collect { unit ->
				val unitMeasurement = viewModel.getUnitMeasurementData().find { it.unit == unit }
				dataBinding.llUnitOfMeasurement.tvSettingName.text =
					getString(R.string.title_unit_of_measurement)
				dataBinding.llUnitOfMeasurement.tvSelectedSetting.text = unitMeasurement?.name ?: ""
			}
		}
	}

	private fun initClickListener() {
		dataBinding.llTheme.root.setSafeOnClickListener {
			val selectedText = dataBinding.llTheme.tvSelectedSetting.text
			val list =
				viewModel.getThemeModeData().filter { it.name != selectedText }.map { it.name }
			openSelectedCarInfoDialog(list, UnitMeasurementDialog.TYPE_THEME)
		}

		dataBinding.llUnitOfMeasurement.root.setSafeOnClickListener {
			val selectedText = dataBinding.llUnitOfMeasurement.tvSelectedSetting.text
			val list = viewModel.getUnitMeasurementData().filter { it.name != selectedText }
				.map { it.name }
			openSelectedCarInfoDialog(list, UnitMeasurementDialog.TYPE_UNIT_MEASUREMENT)
		}
	}

	private fun openSelectedCarInfoDialog(units: List<String>, type: String) {
		val destinationDialogUnitMeasurement = SettingsFragmentDirections
			.actionFragmentSettingToDialogUnitMeasurement(list = units.toTypedArray(), type = type)

		setFragmentResultListener(UnitMeasurementDialog.REQUEST_KEY) { key, bundle ->
			if (key == UnitMeasurementDialog.REQUEST_KEY) {
				val typeResult = bundle.getString(UnitMeasurementDialog.ARG_TYPE) ?: ""
				val unit = bundle.getString(UnitMeasurementDialog.ARG_SELECTED_UNIT) ?: ""
				updateView(typeResult, unit)
			}
		}
		findNavController().navigate(destinationDialogUnitMeasurement)
	}

	private fun updateView(type: String, unit: String) {
		if (type == UnitMeasurementDialog.TYPE_THEME) {
			dataBinding.llTheme.tvSelectedSetting.text = unit
			val selectedMode = viewModel.getThemeModeData().find { it.name == unit }
			selectedMode?.let {
				viewModel.setSelectedThemeMode(it.themeMode)
				changeTheme(it.themeMode)
			}

		} else {
			dataBinding.llUnitOfMeasurement.tvSelectedSetting.text = unit
			val selectedUnit = viewModel.getUnitMeasurementData().find { it.name == unit }
			selectedUnit?.let {
				viewModel.setSelectedUnitMeasurement(it.unit)
			}
		}
	}

	private fun changeTheme(theme: ThemeMode){
		ThemeHelper.applyTheme(theme)
	}
}