package com.stocard.setting.presentation.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.stocard.core.dialog.BaseBottomSheetDialog
import com.stocard.core.util.gone
import com.stocard.setting.R
import com.stocard.setting.databinding.BottomSeetDialogUnitMeassurementTypeBinding
import com.stocard.setting.databinding.LayoutUnitItemBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

/**
 * Created By Rafiqul Hasan
 */
class UnitMeasurementDialog : BaseBottomSheetDialog<BottomSeetDialogUnitMeassurementTypeBinding>() {
	companion object {
		const val REQUEST_KEY = "UNIT_INFO"
		const val ARG_SELECTED_UNIT = "SELECTED_UNIT"
		const val ARG_TYPE = "ARG_TYPE"
		const val TYPE_THEME = "THEME"
		const val TYPE_UNIT_MEASUREMENT = "UNIT_MEASUREMENT"
	}

	private val args: UnitMeasurementDialogArgs by navArgs()

	override val layoutResourceId: Int
		get() = R.layout.bottom_seet_dialog_unit_meassurement_type

	override val bottomSheetBehavior: Int
		get() = BottomSheetBehavior.STATE_HALF_EXPANDED

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initView()
	}

	private fun initView() {
		for (i in 0 until args.list.size) {
			val binding = LayoutUnitItemBinding.inflate(layoutInflater)
			binding.tvSelectedSetting.text = args.list[i]
			if (i == args.list.size - 1) {
				binding.divider.gone()
			}
			binding.root.setOnClickListener {
				setFragmentResult(
					REQUEST_KEY,
					Bundle().apply {
						putString(ARG_TYPE, args.type)
						putString(ARG_SELECTED_UNIT, args.list[i])
					}
				)
				dismiss()
			}
			dataBinding.llContainer.addView(binding.root)
		}
	}
}