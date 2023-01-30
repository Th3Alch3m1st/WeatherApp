package com.stocard.weatherinfo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocard.core.network.Resource
import com.stocard.core.model.UnitMeasurement
import com.stocard.preference.usecase.ISettingPreference
import com.stocard.weatherinfo.data.location.ILocationTracker
import com.stocard.weatherinfo.domain.model.LocationInfo
import com.stocard.weatherinfo.domain.model.WeatherInfoUIModel
import com.stocard.weatherinfo.domain.usecase.WeatherInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created By Rafiqul Hasan
 */
@HiltViewModel
class WeatherInfoViewModel @Inject constructor(
	private val userCase: WeatherInfoUseCase,
	private val locationTracker: ILocationTracker,
	private val settingPreference: ISettingPreference
) :
	ViewModel() {

	private val _locationFlow = Channel<LocationInfo?>()
	val locationFlow = _locationFlow.receiveAsFlow()

	private val _weatherInfoStateFlow =
		MutableStateFlow<Resource<WeatherInfoUIModel>>(Resource.Loading)
	val weatherInfoStateFlow = _weatherInfoStateFlow.asStateFlow()

	fun getLocation() {
		viewModelScope.launch {
			val locationInfo = locationTracker.getCurrentLocation()
			_locationFlow.send(locationInfo)
		}
	}

	fun getWeatherInfo(latitude: Double, longitude: Double) {
		viewModelScope.launch {
			val unit = getMeasureUnit()
			_weatherInfoStateFlow.emit(Resource.Loading)
			val response = userCase.getWeatherInfo(latitude, longitude, unit)
			_weatherInfoStateFlow.emit(response)
		}
	}

	private suspend fun getMeasureUnit() =
		settingPreference.getSelectedUnitMeasurement(UnitMeasurement.METRIC)
}