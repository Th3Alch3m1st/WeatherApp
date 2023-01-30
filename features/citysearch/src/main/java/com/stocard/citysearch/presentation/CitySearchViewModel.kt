package com.stocard.citysearch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocard.citysearch.domain.model.CityUIModel
import com.stocard.citysearch.domain.usecase.ICityUseCase
import com.stocard.core.network.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created By Rafiqul Hasan
 */
@HiltViewModel
class CitySearchViewModel @Inject constructor(private val useCase: ICityUseCase) :
	ViewModel() {
	private val _cities =
		MutableStateFlow<Resource<List<CityUIModel>>>(Resource.Loading)
	val cities = _cities.asStateFlow()

	fun getCities() {
		viewModelScope.launch {
			_cities.emit(Resource.Loading)
			val response = useCase.getCities()
			_cities.emit(response)
		}
	}
}