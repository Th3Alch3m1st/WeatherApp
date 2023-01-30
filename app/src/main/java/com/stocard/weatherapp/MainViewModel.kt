package com.stocard.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocard.core.model.ThemeMode
import com.stocard.preference.usecase.ISettingPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val settingPreference: ISettingPreference):ViewModel() {
	private val _selectedTheme = MutableStateFlow(ThemeMode.DEFAULT)
	val selectedTheme = _selectedTheme.asStateFlow()

	fun getSelectedTheme() {
		viewModelScope.launch {
			_selectedTheme.emit(settingPreference.getSelectedThemeMode(ThemeMode.DEFAULT))
		}
	}
}