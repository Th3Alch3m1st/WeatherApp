package com.stocard.citysearch.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.stocard.citysearch.R
import com.stocard.citysearch.databinding.BottomSheetDialogCitySearchBinding
import com.stocard.citysearch.domain.model.CityUIModel
import com.stocard.core.dialog.BaseBottomSheetDialog
import com.stocard.core.network.Resource
import com.stocard.core.util.GridItemDecoration
import com.stocard.core.util.DebouncingQueryTextListener
import com.stocard.core.util.gone
import com.stocard.core.util.show
import com.google.android.material.bottomsheet.BottomSheetBehavior

import dagger.hilt.android.AndroidEntryPoint

/**
 * Created By Rafiqul Hasan
 */
@AndroidEntryPoint
class CitySearchBottomSheetDialog : BaseBottomSheetDialog<BottomSheetDialogCitySearchBinding>(),
	CitySearchAdapter.CitySearchAdapterCallBack {
	companion object {
		const val REQUEST_KEY = "CITY_INFO"
		const val ARG_SELECTED_CITY = "SELECTED_CITY"
	}

	private val viewModel: CitySearchViewModel by viewModels()
	private lateinit var adapter: CitySearchAdapter

	private var itemDecoration: GridItemDecoration? = null
	private var spanCount = 2
	override val layoutResourceId: Int
		get() = R.layout.bottom_sheet_dialog_city_search

	override val bottomSheetBehavior: Int
		get() = BottomSheetBehavior.STATE_EXPANDED

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel.getCities()
		spanCount =
			if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
				3
			} else {
				2
			}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initView()
		initRecyclerView()
		initObservable()
	}

	override fun onFilterComplete(isEmpty: Boolean, message: String?) {
		fragmentCommunicator?.hideLoader()
		if (isEmpty) {
			dataBinding.viewEmpty.root.show()
			dataBinding.viewEmpty.btnTryAgain.gone()
			dataBinding.viewEmpty.tvTitle.text = message ?: getString(R.string.no_city_found)
		} else {
			dataBinding.viewEmpty.root.gone()
		}
	}

	override fun onCitySelect(cityUIModel: CityUIModel) {
		setFragmentResult(REQUEST_KEY, Bundle().apply {
			putParcelable(ARG_SELECTED_CITY, cityUIModel)
		})
		dismiss()
	}

	private fun initView() {
		with(dataBinding) {
			searchView.setOnQueryTextListener(
				DebouncingQueryTextListener(
					this@CitySearchBottomSheetDialog.lifecycle
				) { newText ->
					newText?.let {
						fragmentCommunicator?.showLoader()
						adapter.filter.filter(it.trim())
					}
				}
			)
			searchView.requestFocus()
			btnCancel.setOnClickListener {
				dismiss()
			}
		}
	}

	private fun initRecyclerView() {
		adapter = CitySearchAdapter(this)
		with(dataBinding) {
			rvCities.apply {
				layoutManager = GridLayoutManager(context, spanCount)
				itemDecoration?.let {
					removeItemDecoration(it)
				}
				itemDecoration = GridItemDecoration(
					requireContext().resources.getDimension(
						com.intuit.sdp.R.dimen._4sdp
					).toInt(),
					spanCount
				)
				addItemDecoration(itemDecoration!!)
			}
			rvCities.adapter = adapter
		}
	}

	private fun initObservable() {
		lifecycleScope.launchWhenStarted {
			viewModel.cities.collect { response ->
				when (response) {
					is Resource.Loading -> {
						fragmentCommunicator?.showLoader()
					}
					is Resource.Success -> {
						fragmentCommunicator?.hideLoader()
						onFilterComplete(response.data.isEmpty())
						adapter.setCityList(response.data)
					}

					is Resource.Error -> {
						onFilterComplete(true, response.exception.message)
						fragmentCommunicator?.hideLoader()
					}
				}
			}
		}
	}
}