package com.stocard.core.util

import androidx.appcompat.app.AppCompatDelegate
import com.stocard.core.model.ThemeMode

/**
 * Created By Rafiqul Hasan
 */
object ThemeHelper {
	fun applyTheme(themeMode: ThemeMode) {
		when (themeMode) {
			ThemeMode.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
			ThemeMode.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
			ThemeMode.BATTERY_SAVER -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
			ThemeMode.DEFAULT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
		}
	}
}