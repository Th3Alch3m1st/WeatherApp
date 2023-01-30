package com.stocard.weatherinfo.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stocard.citysearch.domain.model.CityUIModel
import com.stocard.citysearch.presentation.CitySearchBottomSheetDialog
import com.stocard.core.fragment.BaseFragment
import com.stocard.core.network.Resource
import com.stocard.core.util.*
import com.stocard.weatherinfo.R
import com.stocard.weatherinfo.databinding.FragmentWeatherInfoBinding
import com.stocard.weatherinfo.presentation.adapter.DailyWeatherInfoAdapter
import com.stocard.weatherinfo.presentation.adapter.HourlyWeatherInfoAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created By Rafiqul Hasan
 */
@AndroidEntryPoint
class WeatherInfoFragment : BaseFragment<FragmentWeatherInfoBinding>() {

	private val viewModel: WeatherInfoViewModel by viewModels()
	private var adapterHourlyWeatherInfo: HourlyWeatherInfoAdapter by autoCleared()
	private var adapterDailyWeatherInfo: DailyWeatherInfoAdapter by autoCleared()
	private lateinit var permissionLauncher: ActivityResultLauncher<String>

	override val layoutResourceId: Int
		get() = R.layout.fragment_weather_info

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupToolbar()
		setupMenu()
		initSwipeRefreshLayout()
		initAdapter()
		initRecyclerView()
		checkPermissionAndEnableMyLocation()
	}

	private fun setupToolbar() {
		dataBinding.layoutToolbar.toolbar.title = getString(R.string.simple_weather)
		fragmentCommunicator?.setActionBar(dataBinding.layoutToolbar.toolbar, false)
	}

	private fun setupMenu() {
		(requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
			override fun onPrepareMenu(menu: Menu) {

			}

			override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
				menuInflater.inflate(R.menu.menu, menu)
			}

			override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
				when (menuItem.itemId) {
					R.id.action_search -> {
						navigateToCitySearchFragment()
					}
					R.id.action_setting -> {
						findNavController().navigate(R.id.action_fragment_weatherinfo_to_setting)
					}
					else -> {

					}
				}
				return true
			}
		}, viewLifecycleOwner, Lifecycle.State.RESUMED)
	}

	private fun initSwipeRefreshLayout() {
		dataBinding.swipeRefresh.setOnRefreshListener {
			dataBinding.viewEmpty.root.gone()
			checkPermissionAndEnableMyLocation()
			dataBinding.swipeRefresh.isRefreshing = false
		}
	}

	private fun initAdapter() {
		adapterHourlyWeatherInfo = HourlyWeatherInfoAdapter()
		adapterDailyWeatherInfo = DailyWeatherInfoAdapter()
	}

	private fun initRecyclerView() {
		dataBinding.layoutHourlyForecast.rvWeatherForecast.layoutManager =
			LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
		dataBinding.layoutHourlyForecast.rvWeatherForecast.adapter = adapterHourlyWeatherInfo

		dataBinding.layoutDailyForecast.rvWeatherForecast.layoutManager =
			LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
		dataBinding.layoutDailyForecast.rvWeatherForecast.adapter = adapterDailyWeatherInfo
	}

	private fun checkPermissionAndEnableMyLocation() {
		if (ContextCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_COARSE_LOCATION
			) == PackageManager.PERMISSION_GRANTED
		) {
			checkIfGpsIsEnable()
		} else {
			permissionLauncher = registerForActivityResult(
				ActivityResultContracts.RequestPermission()
			) { isGranted: Boolean ->
				if (isGranted) {
					checkIfGpsIsEnable()
				} else {
					showHideErrorUIForGpsAndPermission(
						getString(R.string.msg_permission),
						getString(R.string.setting)
					) {
						context?.let { ctx ->
							startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
								data =
									Uri.fromParts("package", ctx.packageName, null)
							})
						}
					}
				}
			}
			permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
		}
	}

	private fun checkIfGpsIsEnable() {
		val locationManager =
			requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
		val isGpsEnabled =
			locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(
				LocationManager.GPS_PROVIDER
			)
		if (isGpsEnabled) {
			getLocationAndWeatherInfo()
		} else {
			fragmentCommunicator?.hideLoader()
			showHideErrorUIForGpsAndPermission(
				getString(R.string.msg_enable_your_gps),
				getString(R.string.open_city_list)
			) {
				navigateToCitySearchFragment()
			}
		}
	}

	private fun getLocationAndWeatherInfo() {
		initObserver()
		viewModel.getLocation()
	}

	private fun initObserver() {
		lifecycleScope.launchWhenStarted {
			viewModel.weatherInfoStateFlow.collect { response ->
				when (response) {
					is Resource.Loading -> {
						fragmentCommunicator?.showLoader()
						showHideErrorUI(null)
					}
					is Resource.Success -> {
						fragmentCommunicator?.hideLoader()
						showHideErrorUI(null)
						showWeatherInfoUI(isVisible = true)
						dataBinding.currentWeatherInfo = response.data.currentWeatherInfo
						dataBinding.layoutCurrentWeatherInfo.root.show()
						dataBinding.layoutHourlyForecast.root.show()
						dataBinding.layoutDailyForecast.root.show()
						adapterHourlyWeatherInfo.setHourlyWeatherInfo(response.data.hourly)
						adapterDailyWeatherInfo.setHourlyWeatherInfo(response.data.daily)
					}

					is Resource.Error -> {
						fragmentCommunicator?.hideLoader()
						showHideErrorUI(response.exception)
					}
				}
			}
		}

		lifecycleScope.launchWhenStarted {
			viewModel.locationFlow.collect { location ->
				if (location == null) {
					fragmentCommunicator?.hideLoader()
					showHideErrorUIForGpsAndPermission(
						getString(R.string.msg_location_not_found),
						getString(R.string.open_city_list)
					) {
						navigateToCitySearchFragment()
					}
				} else {
					dataBinding.tvCurrentLocation.text = location.name
					viewModel.getWeatherInfo(location.lat, location.lon)
				}
			}
		}
	}

	@SuppressLint("SetTextI18n")
	private fun navigateToCitySearchFragment() {
		setFragmentResultListener(CitySearchBottomSheetDialog.REQUEST_KEY) { key, bundle ->
			if (key == CitySearchBottomSheetDialog.REQUEST_KEY) {
				val cityUIModel =
					bundle.parcelableData<CityUIModel>(CitySearchBottomSheetDialog.ARG_SELECTED_CITY)
				cityUIModel?.let {
					initObserver()
					initSwipeRefreshLayout()
					viewModel.getWeatherInfo(cityUIModel.lat, cityUIModel.lon)
					dataBinding.tvCurrentLocation.text =
						"${cityUIModel.cityName}, ${cityUIModel.country}"
				}
			}
		}
		findNavController().navigate(R.id.action_fragment_weather_info_to_city_search)
	}

	private fun showHideErrorUI(exception: Throwable?) {
		exception?.let {
			dataBinding.viewEmpty.root.show()
			dataBinding.viewEmpty.tvTitle.text = it.localizedMessage
			dataBinding.viewEmpty.btnTryAgain.text = getString(com.stocard.core.R.string.retry)
			dataBinding.viewEmpty.btnTryAgain.setOnClickListener {
				checkPermissionAndEnableMyLocation()
			}
			showWeatherInfoUI(isVisible = false)
		} ?: run {
			dataBinding.viewEmpty.root.gone()
		}
	}

	private fun showHideErrorUIForGpsAndPermission(
		title: String,
		buttonText: String,
		clickListener: () -> Unit
	) {
		dataBinding.viewEmpty.root.show()
		dataBinding.viewEmpty.tvTitle.text = title
		dataBinding.viewEmpty.btnTryAgain.text = buttonText
		dataBinding.viewEmpty.btnTryAgain.setOnClickListener {
			clickListener.invoke()
		}
		showWeatherInfoUI(false)
	}

	private fun showWeatherInfoUI(isVisible: Boolean) {
		if (isVisible) {
			dataBinding.layoutCurrentWeatherInfo.root.show()
			dataBinding.layoutDailyForecast.root.show()
			dataBinding.layoutHourlyForecast.root.show()
			dataBinding.tvCurrentLocation.show()
		} else {
			dataBinding.layoutCurrentWeatherInfo.root.invisible()
			dataBinding.layoutDailyForecast.root.invisible()
			dataBinding.layoutHourlyForecast.root.invisible()
			dataBinding.tvCurrentLocation.invisible()
		}
	}
}