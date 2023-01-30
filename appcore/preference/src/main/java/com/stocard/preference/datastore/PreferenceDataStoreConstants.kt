package com.stocard.preference.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * Created By Rafiqul Hasan
 */
object PreferenceDataStoreConstants {
	val UNIT_MEASUREMENT_PREF_KEY = stringPreferencesKey(Keys.UNIT_MEASUREMENT)
	val THEME_PREF_KEY = stringPreferencesKey(Keys.THEME_MODE)
}