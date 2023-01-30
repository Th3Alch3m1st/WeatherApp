package com.stocard.weatherinfo.fake

import com.stocard.weatherinfo.data.location.ILocationTracker
import com.stocard.weatherinfo.domain.model.LocationInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rafiqul Hasan
 */
@Singleton
class FakeLocationTrackerImpl @Inject constructor() : ILocationTracker {
	companion object {
		const val LOCATION_LAT = 52.5067614
		const val LOCATION_LON = 13.2846506
		const val MSG_LOCATION_ERROR = "Location not found! Please check location permission is given and GPS is enabled!"
	}

	var isTestError = false
	override suspend fun getCurrentLocation(): LocationInfo? {
		return if (isTestError) {
			null
		} else {
			LocationInfo(
				lat = LOCATION_LAT,
				lon = LOCATION_LON,
				name = "Berlin"
			)
		}
	}
}