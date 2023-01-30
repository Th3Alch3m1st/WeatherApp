package com.stocard.preference.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.stocard.preference.datastore.domain.IPreferenceDataStoreAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

/**
 * Created By Rafiqul Hasan
 */
class PreferenceDataStoreAPIImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
	IPreferenceDataStoreAPI {
	override suspend fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
		val result = dataStore.data.catch { exception ->
			if (exception is IOException) {
				emit(emptyPreferences())
			} else {
				throw exception
			}
		}.map { preferences ->
			val result = preferences[key] ?: defaultValue
			result
		}

		return result
	}

	override suspend fun <T> getFirstPreference(key: Preferences.Key<T>, defaultValue: T): T {
		return dataStore.data.first()[key] ?: defaultValue
	}

	override suspend fun <T> putPreference(key: Preferences.Key<T>, value: T) {
		dataStore.edit {   preferences ->
			preferences[key] = value
		}
	}

	override suspend fun <T> removePreference(key: Preferences.Key<T>) {
		dataStore.edit { preferences ->
			preferences.remove(key)
		}
	}

	override suspend fun clearAllPreference() {
		dataStore.edit { preferences ->
			preferences.clear()
		}
	}
}